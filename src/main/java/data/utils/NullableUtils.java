package data.utils;

import java.util.Objects;
import java.util.function.Consumer;

public interface NullableUtils {

    static <T> void executeNonNull(T obj, Runnable action) {
        if (Objects.nonNull(obj)) {
            action.run();
        }
    }

    static <T> void executeIsNull(T obj, Runnable action) {
        if (Objects.isNull(obj)) {
            action.run();
        }
    }

    static void executeNonNull(Runnable r) {
        executeNonNull(r, r);
    }

    static <T> void executeNonNull(T obj, Consumer<T> consumer) {
        executeNonNull(obj, () -> consumer.accept(obj));
    }
}
