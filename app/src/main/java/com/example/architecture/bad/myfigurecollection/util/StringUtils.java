package com.example.architecture.bad.myfigurecollection.util;

import android.text.TextUtils;

/**
 * Created by spawn on 28/06/16.
 */
public class StringUtils {

    public static String extractStringBeforeSeparatorRepeatedNTimes(String value, char separator, int repetition) {

        if (value == null || "".equals(value)) {
            return "";
        }

        int index = extractIndexOfSepratorRepeated(value, separator, 0, repetition, 0);

        if(index == -1) return value;

        return value.substring(0, index).trim();
    }

    public static String extractStringAfterSeparatorRepeatedNTimes(String value, char separator, int repetition) {

        if (value == null || "".equals(value)) {
            return "";
        }

        int index = extractIndexOfSepratorRepeated(value, separator, 0, repetition, 0);

        if(index == -1) return value;

        return value.substring(++index).trim();
    }

    private static int extractIndexOfSepratorRepeated(String name, char separator, int start, int repetition, int acc) {

        if (repetition == acc) return --start;

        int indexNSeparator = name.indexOf(separator, start);

        return (indexNSeparator == -1) ? --start : extractIndexOfSepratorRepeated(name, separator, ++indexNSeparator, repetition, ++acc);

    }

}
