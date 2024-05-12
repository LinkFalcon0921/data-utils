package data.utils;

import lombok.NonNull;

import java.util.function.Predicate;

public interface InstanceUtils {
    static <T> Predicate<T> is(@NonNull Class<? extends T> instanceClass){
        return instanceClass::isInstance;
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> isNot(@NonNull Class<? extends T> instanceClass){
        return (Predicate<T>) is(instanceClass).negate();
    }
}
