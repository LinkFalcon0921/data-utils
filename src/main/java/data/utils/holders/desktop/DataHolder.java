package data.utils.holders.desktop;

import data.utils.holders.EDataHolderKey;
import data.utils.holders.desktop.listeners.IDataHolderListener;
import data.utils.observables.DataHolderObserverSupport;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
/**TODO make an non-desktop implementation.*/
public abstract class DataHolder<H> {
    protected final AtomicReference<H> dataRef;

    //    private final List<DataHolderListener<H>> callListeners;
    protected DataHolderObserverSupport<H> callHolderObserver;

    public DataHolder(H value) {
        dataRef = new AtomicReference<>(value);
        this.callHolderObserver = new DataHolderObserverSupport<>();
    }

    public H getCurrent() {
        return dataRef.get();
    }

    /**
     * Notify all listeners.
     */
    public void setCurrent(@NonNull H data) {
        H newValue = this.copyOf(data);

        H previous = this.dataRef.getAndSet(newValue);

        if (Objects.equals(previous, newValue)) {
            return;
        }

        this.fireChange(EDataHolderKey.ANY, previous, newValue);
    }

    public <HS extends IDataHolderListener<H>> void addListener(HS dataListener) {
        this.addListener(List.of(EDataHolderKey.ANY), List.of(dataListener));
    }

    public <HS extends IDataHolderListener<H>> void addListener(EDataHolderKey key, HS dataListener) {
        this.addListener(List.of(key), List.of(dataListener));
    }

    public <HS extends IDataHolderListener<H>> void addListener(List<EDataHolderKey> keys, HS dataListener) {
        this.addListener(keys, List.of(dataListener));
    }

    public void addListener(List<? extends IDataHolderListener<H>> dataListeners) {
        this.addListener(List.of(EDataHolderKey.ANY), dataListeners);
    }

    public void addListener(List<EDataHolderKey> keys, List<? extends IDataHolderListener<H>> dataListeners) {
        if (keys.contains(EDataHolderKey.ANY)) {
            keys = List.of(EDataHolderKey.ANY);
        }

        for (EDataHolderKey key : keys) {
            for (IDataHolderListener<? extends H> listener : dataListeners) {
                this.callHolderObserver.addListener(key, listener);
            }
        }
    }

    /**
     * This method returns the same received data by default, so implement it in case of changes.
     */
    protected abstract H copyOf(H data);

    protected synchronized void fireChange(
            EDataHolderKey propertyChange,
            H currentCallFiles,
            H updatedCallFiles) {
        final Supplier<H> hSupplier = () -> this.copyOf(updatedCallFiles);
        this.callHolderObserver.firePropertyChange(propertyChange, currentCallFiles, hSupplier);
    }

}
