package data.utils.tasks;

import lombok.NonNull;

import java.util.function.BiConsumer;

public interface BiConsumerTask<C, E> extends BiConsumer<C, E> {
    @NonNull
    default BiConsumerTask<C, E> andThen(BiConsumerTask<? super C, ? super E> after) {
        return (BiConsumerTask<C, E>) BiConsumer.super.andThen(after);
    }
}
