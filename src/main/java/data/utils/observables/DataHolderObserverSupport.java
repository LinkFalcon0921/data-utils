package data.utils.observables;

import data.utils.holders.EDataHolderKey;
import data.utils.holders.desktop.listeners.IDataHolderListener;
import lombok.NonNull;

import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;

public final class DataHolderObserverSupport<H> extends DataHolderSupport<H> {
    public static final EDataHolderKey[] ANY_PROPERTIES = EDataHolderKey.values();
    public static final EDataHolderKey[] WITHOUT_ANY_KEYS = EDataHolderKey.withoutAnyHolderKeys();
    public static final EDataHolderKey ANY_PROPERTY = EDataHolderKey.ANY;

    public DataHolderObserverSupport() {
        super();
        this.propertyObserver = new PropertyChangeSupport(this);
    }
}
