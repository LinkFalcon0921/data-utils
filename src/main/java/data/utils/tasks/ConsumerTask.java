package data.utils.tasks;

import lombok.NonNull;

import java.util.function.Consumer;

public interface ConsumerTask<C> extends Consumer<C> {
    @Override
    @NonNull
    default ConsumerTask<C> andThen(@NonNull Consumer<? super C> after) {
        return c -> {
            accept(c);
            after.accept(c);
        };
    }
}
