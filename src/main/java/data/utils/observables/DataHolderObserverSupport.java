package data.utils.observables;

import data.utils.holders.EDataHolderKey;
import data.utils.holders.desktop.listeners.IDataHolderListener;
import lombok.NonNull;

import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

public final class DataHolderObserverSupport<H> {
    public static final EDataHolderKey[] ANY_PROPERTIES = EDataHolderKey.values();
    public static final EDataHolderKey[] WITHOUT_ANY_KEYS = EDataHolderKey.withoutAnyHolderKeys();
    public static final EDataHolderKey ANY_PROPERTY = EDataHolderKey.ANY;

    private final PropertyChangeSupport propertyObserver;

    public DataHolderObserverSupport() {
        propertyObserver = new PropertyChangeSupport(this);
    }

    public void addListener(EDataHolderKey key, IDataHolderListener<? extends H> listener) {
        this.propertyObserver.addPropertyChangeListener(key.name(), listener);
    }

    @SafeVarargs
    public final void addListener(EDataHolderKey key, @NonNull IDataHolderListener<? extends H>... listeners) {
        for (IDataHolderListener<? extends H> listener : listeners) {
            this.addListener(key, listener);
        }
    }

    /**
     * If {@code ANY} is inside the list of properties, will call all property keys.<br/>
     * Otherwise, it will fire only the presented values.
     */
    public void firePropertyChange(@NonNull List<EDataHolderKey> propertyNames, H oldValue, Supplier<H> newValue) {
        this.firePropertyChange(propertyNames, oldValue, newValue.get());
    }

    public void firePropertyChange(@NonNull EDataHolderKey propertyName, H oldValue, Supplier<H> newValue) {
        this.firePropertyChange(propertyName, oldValue, newValue.get());
    }

    /**
     * If {@code ANY} is inside the list of properties, will call all property keys.<br/>
     * Otherwise, it will fire only the presented values.
     */
    public void firePropertyChange(@NonNull Collection<EDataHolderKey> propertyNames, H oldValue, H newValue) {
        HashSet<@NonNull EDataHolderKey> holderKeys = new HashSet<>(propertyNames);
        holderKeys.add(EDataHolderKey.ANY);

        for (EDataHolderKey holderKey : holderKeys) {
            this.propertyObserver.firePropertyChange(holderKey.name(), oldValue, newValue);
        }
    }

    /**
     * If {@code propertyName} is null, throws an exception.
     * If the value of {@code propertyName} is {@code ANY} will call all the property keys.<br/>
     * In case {@code propertyName} is other value, {@code ANY} also will be called.
     */
    public void firePropertyChange(@NonNull EDataHolderKey propertyName, H oldValue, H newValue) {
        this.firePropertyChange(List.of(propertyName), oldValue, newValue);
    }
}
