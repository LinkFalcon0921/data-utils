package data.utils.tasks;

import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * Chain of {@link CompletableFuture} of a given {@code T} class.
 *
 * @apiNote This class is final. Joiner and creation functions create new instances.<br/>
 */
public final class TaskAsyncChain<T> {
    private final CompletableFuture<T> task;

    // Constructors

    private TaskAsyncChain(CompletableFuture<T> task) {
        this.task = task;
    }

    private TaskAsyncChain(ITask<T> supplier) {
        this.task = CompletableFuture.supplyAsync(supplier);
    }

    private TaskAsyncChain(ITask<T> supplier, Executor executor) {
        this.task = CompletableFuture.supplyAsync(supplier, executor);
    }

    private TaskAsyncChain(ITask<T> supplier, Executor executor, Supplier<T> defaultValue) {
        this.task = CompletableFuture.supplyAsync(supplier, executor)
                .exceptionallyAsync(th -> defaultValue.get());
    }

    // Create actions

    /**
     * <h3>** Creation Function **</h3>
     * <p>Create an new instance.</p>
     */
    public static <R> TaskAsyncChain<R> createTask(ITask<R> supplier) {
        return new TaskAsyncChain<>(supplier);
    }

    /**
     * <h3>** Creation Function **</h3>
     * <p>Create an new instance. Use an executor.</p>
     */
    public static <R> TaskAsyncChain<R> createTask(Executor executor, ITask<R> supplier) {
        return new TaskAsyncChain<>(supplier, executor);
    }

    /**
     * <h3>** Creation Function **</h3>
     * <p>Create an new instance. Use an executor.</p>
     */
    public static <R> TaskAsyncChain<R> createTask(Executor executor, ITask<R> supplier, Supplier<R> defaultValue) {
        return new TaskAsyncChain<>(supplier, executor, defaultValue);
    }

    // Then actions

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance.</p>
     *
     * @see TaskAsyncChain Class reference.
     */
    public <V> TaskAsyncChain<V> then(FunctionTask<? super T, V> functionTask) {
        return thenDefaults(null, functionTask, null, null);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor.</p>
     *
     * @see #thenDefault(Executor, FunctionTask, Supplier) Similar method.
     */
    public <V> TaskAsyncChain<V> then(
            Executor executor,
            FunctionTask<? super T, V> functionTask) {
        return thenDefaults(executor, functionTask, null, null);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor. Also, in case of throw am exception,
     * * returns an default value.</p>
     *
     * @see #thenDefault(Executor, FunctionTask, Supplier) Similar method.
     */
    public <V> TaskAsyncChain<V> then(
            FunctionTask<? super T, V> functionTask,
            Supplier<? extends V> defaultValue) {

        return thenDefaults(null, functionTask, null, defaultValue);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor. Also, in case of throw am exception,
     * returns an default value.</p>
     *
     * @param futureDefault the default value of the new task.
     * @see #then(FunctionTask) Similar method.
     * @see #then(FunctionTask, Supplier) Similar method.
     * @see #then(Executor, FunctionTask) Similar method.
     */
    public <V> TaskAsyncChain<V> thenDefault(
            FunctionTask<? super T, V> functionTask,
            Supplier<? extends V> futureDefault) {

        return thenDefaults(null, functionTask, null, futureDefault);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor. Also, in case of throw am exception,
     * returns an default value.</p>
     *
     * @param futureDefault the default value of the new task.
     * @see #then(FunctionTask) Similar method.
     * @see #then(FunctionTask, Supplier) Similar method.
     * @see #then(Executor, FunctionTask) Similar method.
     */
    public <V> TaskAsyncChain<V> thenDefault(
            Executor executor,
            FunctionTask<? super T, V> functionTask,
            Supplier<? extends V> futureDefault) {

        return thenDefaults(executor, functionTask, null, futureDefault);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor. Also, in case of throw am exception, returns an default value.</p>
     *
     * @param resultDefault the default value of the new task.
     * @see #then(FunctionTask) Similar method.
     * @see #then(FunctionTask, Supplier) Similar method.
     * @see #then(Executor, FunctionTask) Similar method.
     */
    public <V> TaskAsyncChain<V> thenOnFail(
            Executor executor,
            FunctionTask<? super T, V> functionTask,
            Supplier<T> resultDefault) {

        return thenDefaults(executor, functionTask, resultDefault, null);
    }

    /**
     * <h3>** Joiner Function **</h3>
     * <p>Create an new instance. Use an executor. Also, in case of throw am exception, returns an default value.</p>
     *
     * @param resultDefault the default value of the actual task.
     * @param futureDefault the default value of the new task.
     * @see #then(FunctionTask) Similar method.
     * @see #then(FunctionTask, Supplier) Similar method.
     * @see #then(Executor, FunctionTask) Similar method.
     */
    public <V> TaskAsyncChain<V> thenDefaults(
            Executor executor,
            FunctionTask<? super T, V> functionTask,
            Supplier<T> resultDefault,
            Supplier<? extends V> futureDefault) {

        CompletableFuture<V> nextFuture = this.task.thenComposeAsync(result -> {
            if (ObjectUtils.allNull(result, resultDefault, futureDefault)) {
                return CompletableFuture.failedFuture(new NullPointerException());
            }

            T finalResult = ObjectUtils.getFirstNonNull(() -> result, resultDefault);
            Supplier<V> apply = () -> functionTask.apply(finalResult);

            CompletableFuture<V> suppliedAsync = (Objects.nonNull(executor)) ?
                    CompletableFuture.supplyAsync(apply, executor) :
                    CompletableFuture.supplyAsync(apply);

            V v = ObjectUtils.getFirstNonNull(futureDefault, () -> null);
            return suppliedAsync.exceptionallyAsync(th -> v);
        });

        return new TaskAsyncChain<>(nextFuture);
    }

    // accept actions.

    /**
     * <h3>**Terminal function**</h3>
     * <p> <>Nota: This functions just consume the result but does no return an {@link TaskAsyncChain}.</p>
     * <p>
     * Receive a consumer function that is called after the previous {@code Task} ends.
     */
    public void accept(ConsumerTask<? super T> functionTask) {
        this.task.thenComposeAsync(result -> {
            if (Objects.isNull(result)) {
                return CompletableFuture.failedFuture(new NullPointerException());
            }

            return CompletableFuture.completedFuture(result);
        }).thenAcceptAsync(functionTask);
    }

    public void accept(@NonNull Executor executor, ConsumerTask<? super T> functionTask) {
        this.task.thenComposeAsync(result -> {
            if (Objects.isNull(result)) {
                return CompletableFuture.failedFuture(new NullPointerException());
            }

            return CompletableFuture.completedFuture(result);
        }).thenAcceptAsync(functionTask, executor);
    }
}
