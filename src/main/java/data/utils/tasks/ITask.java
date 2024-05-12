package data.utils.tasks;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface ITask<V> extends Callable<V>, Supplier<V> {
    @Override
    default V call() {
        return get();
    };
}
