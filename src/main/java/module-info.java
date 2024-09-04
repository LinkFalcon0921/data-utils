module flintCore.data.utils {
    requires static lombok;
    requires org.apache.commons.lang3;
    requires java.desktop;

    // Generals
    exports data.utils;

    // Collections
    exports data.utils.collections;

    // Holders
    exports data.utils.holders;
    exports data.utils.holders.desktop.events;
    exports data.utils.holders.desktop.listeners;

    // Observables
    exports data.utils.observables;
    exports data.utils.observables.swing;

    // Tasks
    exports data.utils.tasks;

    // Time
    exports data.utils.time;
    exports data.utils.holders.desktop;
}