package data.utils.holders.desktop.events;

import data.utils.holders.EDataHolderKey;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class DataHolderEvent<T> extends PropertyChangeEvent {

    protected final Class<T> typeData;
    @Getter
    private final EDataHolderKey keyProperty;

    public DataHolderEvent(
            Object source,
            EDataHolderKey propertyName,
            T oldValue,
            T newValue,
            @NonNull Class<T> typeData) {
        super(source, propertyName.name(), oldValue, newValue);
        this.typeData = typeData;
        this.keyProperty = propertyName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getNewValue() {
        return (T) super.getNewValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getOldValue() {
        return (T) super.getOldValue();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<DataHolderEvent<T>> castEvent(
            PropertyChangeEvent evt) throws ClassCastException {

        if (Objects.isNull(evt)) {
            return Optional.empty();
        }

        if (evt instanceof DataHolderEvent<?>) {
            DataHolderEvent<?> holderEvent = (DataHolderEvent<?>) evt;
            return Optional.of((DataHolderEvent<T>) holderEvent);
        }

        T castedOldValue = (T) evt.getOldValue();
        T castedNewValue = (T) evt.getNewValue();

        if (ObjectUtils.allNull(castedOldValue, castedNewValue)) {
            return Optional.empty();
        }

        T tValue = ObjectUtils.getIfNull(castedOldValue, () -> castedNewValue);

        Class<T> castedClass = (Class<T>) tValue.getClass();

        DataHolderEvent<T> holderEvent;

        try {
            holderEvent = new DataHolderEvent<>(
                    evt.getSource(),
                    EDataHolderKey.valueOf(evt.getPropertyName()),
                    castedOldValue,
                    castedNewValue,
                    castedClass
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            String dataValues = Arrays.toString(EDataHolderKey.values());

            String formatted = String.format("Require to send a valid property: %s.",
                    dataValues);

            throw new IllegalArgumentException(formatted);
        }

        return Optional.of(holderEvent);
    }
}
