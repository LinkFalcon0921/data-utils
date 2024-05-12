package data.utils.tasks;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionTask<T, R> extends Function<T, R> {
}
