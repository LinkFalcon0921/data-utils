package data.utils.holders.desktop;

public interface ISeparators {
    /**
     * By default, a holder of {@link #getCommaWithSpaceSeparator()} to be used in entire application.
     */
    DataHolder<String> DEFAULT_DATA_HOLDER = new SimpleDataHolder<>(getCommaWithSpaceSeparator());

    /**
     * Will depends on the operative System.
     */
    static String getSystemLineSeparator() {
        return System.lineSeparator();
    }

    /*" "*/
    static String getSpaceSeparator() {
        return " ";
    }

    /**
     * ""
     */
    static String getEmptySeparator() {
        return "";
    }

    /**
     * ","
     */
    static String getCommaSeparator() {
        return ",";
    }

    /**
     * ", "
     */
    static String getCommaWithSpaceSeparator() {
        return getCommaSeparator() + getSpaceSeparator();
    }
}
