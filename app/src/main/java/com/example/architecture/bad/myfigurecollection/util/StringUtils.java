package com.example.architecture.bad.myfigurecollection.util;

/**
 * Created by spawn on 28/06/16.
 */
public class StringUtils {

    public static String extractStringBeforeSeparatorRepeatedNTimes(String value, char separator, int times) {

        if (value == null || "".equals(value)) {
            return "";
        }

        int index = extractIndexOfSeparatorRepeatedNTimes(value, separator, 0, times, 0);

        if(index == -1) return value;

        return value.substring(0, index).trim();
    }

    public static String extractStringAfterSeparatorRepeatedNTimes(String value, char separator, int times) {

        if (value == null || "".equals(value)) {
            return "";
        }

        int index = extractIndexOfSeparatorRepeatedNTimes(value, separator, 0, times, 0);

        if(index == -1) return value;

        return value.substring(++index).trim();
    }

    private static int extractIndexOfSeparatorRepeatedNTimes(String name, char separator, int start, int times, int acc) {

        if (times == acc) return --start;

        int indexNSeparator = name.indexOf(separator, start);

        return (indexNSeparator == -1) ? --start : extractIndexOfSeparatorRepeatedNTimes(name, separator, ++indexNSeparator, times, ++acc);

    }

}
