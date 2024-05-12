package data.utils.holders;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

@NoArgsConstructor
@Deprecated
public class DataHolderKeyUtils {

    public String buildAsString(@NonNull final EDataHolderKey... keys) throws IndexOutOfBoundsException {
        if (ObjectUtils.isEmpty(keys) || ObjectUtils.allNull((Object[]) keys)) {
            throw new IndexOutOfBoundsException("Require one value at least.");
        }

        if (keys.length == 1) {
            return keys[0].name();
        }

        StringJoiner joiner = new StringJoiner(EDataHolderKey.DELIMITER);

        for (EDataHolderKey holderKey : keys) {
            joiner.add(holderKey.name());
        }

        return joiner.toString();
    }

    public EDataHolderKey[] buildAsKeys(String keys) throws StringIndexOutOfBoundsException {
        if (StringUtils.isBlank(keys)) {
            throw new StringIndexOutOfBoundsException("Require a non-empty text");
        }

        String[] splitKeys = keys.split(EDataHolderKey.DELIMITER);
        EDataHolderKey[] result = new EDataHolderKey[splitKeys.length];

        for (int keyIndex = 0; keyIndex < splitKeys.length; keyIndex++) {
            result[keyIndex] = EDataHolderKey.valueOf(splitKeys[keyIndex]);
        }

        return result;
    }
}
