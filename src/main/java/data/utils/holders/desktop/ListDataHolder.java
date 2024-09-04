package data.utils.holders.desktop;

import data.utils.collections.ICollectionUtils;
import data.utils.holders.EDataHolderKey;
import lombok.NonNull;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Use of not repeatable instances.
 * Data holders use (for listeners only) the class name to notify.
 */
public class ListDataHolder<H extends Comparable<H>> extends DataHolder<Set<H>> {

    private static final int INITIAL_CAPACITY = 5;
    private static final float LOAD_FACTOR = .18f;

    /**
     * Set mutable ref
     */
    public ListDataHolder() {
        this(new HashSet<>());
    }

    /**
     * @see #ListDataHolder()
     */
    public ListDataHolder(final Collection<H> hs) {
        super(new HashSet<>(hs.size(), LOAD_FACTOR));
        this.dataRef.get().addAll(hs);
    }

    /**
     * Notifies all listeners.
     * This method uses equals of the list children.
     */
    public void setCurrent(@NonNull final Set<H> data) {
        Set<H> currentValue = supplyNewSet(dataRef.get());

        // Attempt to atomically update the AtomicReference with the new HashSet
        // If the current value has changed since it was last checked,
        // the update fails and the method returns early
        if (currentValue.equals(data)) {
            return;
        }

        // Prepare the new value by creating a new set and adding all elements from data
        EDataHolderKey holderKey;

        Set<H> newValue;
        newValue = supplyNewSet(data);

        if (ICollectionUtils.Filter.notContainsAll(currentValue).test(newValue)) {
            holderKey = EDataHolderKey.UPDATE;
            fireChange(holderKey, currentValue, newValue);
        }

        Set<H> copyNew;

        if (ICollectionUtils.Filter.hasDifferences(currentValue).test(newValue)) {
            holderKey = EDataHolderKey.INSERT;
            copyNew = supplyNewSet(newValue);
            copyNew.removeAll(currentValue);
            fireChange(holderKey, currentValue, copyNew);
        }

        if (ICollectionUtils.Filter.hasDifferences(newValue).test(currentValue)) {
            holderKey = EDataHolderKey.CLEAR;

            copyNew = supplyNewSet(currentValue);
            copyNew.removeAll(newValue);

            fireChange(holderKey, currentValue, copyNew);
        }

        this.dataRef.set(newValue);

    }

    /**
     * Eliminate al the data from holder. It calls all listeners.
     */
    public void clear() {
        this.setCurrent(Collections.emptyList());
    }

    public void setCurrent(@NonNull final Collection<H> data) {
        this.setCurrent(supplyNewSet(data));
    }

    @Override
    protected Set<H> copyOf(Set<H> data) {
        return new HashSet<>(data);
    }

    /**
     * Notifies all listeners.
     */
    @SafeVarargs
    public final void add(@NonNull H... data) {
        updateCurrentDataBy(cl -> {
            Set<H> newValue = supplyNewSet(cl);
            newValue.addAll(Arrays.asList(data));
            return newValue;
        });
    }

    /**
     * Notifies all listeners.
     */
    @SafeVarargs
    public final void remove(@NonNull H... data) {
        updateCurrentDataBy(cl -> {
            Set<H> newValue = supplyNewSet(cl);
            Arrays.asList(data).forEach(newValue::remove);
            return newValue;
        });
    }

    private Set<H> supplyNewSet(final Collection<H> collection) {
        HashSet<H> set = new HashSet<>(INITIAL_CAPACITY, LOAD_FACTOR);

        if (Objects.isNull(collection)) {
            return set;
        }

        set.addAll(collection);

        return set;
    }

    protected void updateCurrentDataBy(
            final UnaryOperator<Set<H>> updateFunction) {
        Set<H> updatedCallFiles = updateFunction.apply(dataRef.get());
        setCurrent(updatedCallFiles);
    }
}
