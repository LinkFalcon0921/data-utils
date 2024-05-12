package data.utils.holders;

import data.utils.ESystemLineSeparator;
import data.utils.holders.desktop.SimpleDataHolder;

import static data.utils.ESystemLineSeparator.UNIX;

public class SystemSeparatorHolder extends SimpleDataHolder<ESystemLineSeparator> {
    private static SystemSeparatorHolder systemSeparatorHolder;

    public static SystemSeparatorHolder getGlobalHolder() {
        if (systemSeparatorHolder == null) {
            synchronized (SystemSeparatorHolder.class) {
                if (systemSeparatorHolder == null) {
                    systemSeparatorHolder = new SystemSeparatorHolder(UNIX);
                }
            }
        }

        return systemSeparatorHolder;
    }

    public SystemSeparatorHolder(ESystemLineSeparator value) {
        super(value);
    }
}
