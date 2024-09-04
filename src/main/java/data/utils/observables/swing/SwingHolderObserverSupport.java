package data.utils.observables.swing;

import data.utils.observables.DataHolderSupport;

import javax.swing.event.SwingPropertyChangeSupport;

/**Events will be tasked in EDT(Swing GUI thread).*/
public class SwingHolderObserverSupport<H> extends DataHolderSupport<H> {

    public SwingHolderObserverSupport() {
        super();
        this.propertyObserver =  new SwingPropertyChangeSupport(this, true);
    }
}
