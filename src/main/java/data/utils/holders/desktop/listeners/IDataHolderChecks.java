package data.utils.holders.desktop.listeners;

import lombok.NonNull;

import java.util.function.Predicate;

public interface IDataHolderChecks {
    static <T> Predicate<T> isInstanceOf(@NonNull Class<T> typeClass) {
        return typeClass::isInstance;
    }
}
