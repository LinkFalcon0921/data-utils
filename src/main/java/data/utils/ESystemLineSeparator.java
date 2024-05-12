package data.utils;

import lombok.Getter;

@Getter

public enum ESystemLineSeparator {
    WINDOWS("\r\n"),
    UNIX("\n");

    private final String separator;

    ESystemLineSeparator(String separator) {
        this.separator = separator;
    }

}
