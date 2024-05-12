package data.utils.holders.desktop.listeners;


import data.utils.holders.desktop.events.DataHolderEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface IDataHolderListener<T> extends PropertyChangeListener {
    void propertyChange(DataHolderEvent<T> evt);

    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        DataHolderEvent.<T>castEvent(evt)
                .ifPresent(this::propertyChange);
    }
}
