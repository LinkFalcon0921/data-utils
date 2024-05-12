package data.utils.holders;

import org.apache.commons.lang3.ArrayUtils;

public enum EDataHolderKey {
    INSERT, CLEAR, UPDATE, ANY;

    public static final String DELIMITER = "_-_";

    public static EDataHolderKey[] withoutAnyHolderKeys() {
        return ArrayUtils.remove(values(), ANY.ordinal());
    }
}
