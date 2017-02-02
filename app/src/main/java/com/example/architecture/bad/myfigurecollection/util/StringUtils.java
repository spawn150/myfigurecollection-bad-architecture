package com.example.architecture.bad.myfigurecollection.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by spawn on 28/06/16.
 */
public class StringUtils {

    public static String extractStringBeforeSeparatorRepeatedNTimes(String value, char separator, int times) {

        if (isEmptyValue(value)) {
            return "";
        }

        int index = extractIndexOfSeparatorRepeatedNTimes(value, separator, 0, times, 0);

        if (index == -1) return value;

        return value.substring(0, index).trim();
    }

    public static String extractStringAfterSeparatorRepeatedNTimes(String value, char separator, int times) {

        if (isEmptyValue(value)) {
            return "";
        }

        int index = extractIndexOfSeparatorRepeatedNTimes(value, separator, 0, times, 0);

        if (index == -1) return value;

        return value.substring(++index).trim();
    }

    private static boolean isEmptyValue(String value) {
        return value == null || "".equals(value);
    }

    private static int extractIndexOfSeparatorRepeatedNTimes(String name, char separator, int start, int times, int acc) {

        if (times == acc) return --start;

        int indexNSeparator = name.indexOf(separator, start);

        return (indexNSeparator == -1) ? --start : extractIndexOfSeparatorRepeatedNTimes(name, separator, ++indexNSeparator, times, ++acc);

    }

    public static String formatDate(Date date, String defaultValue) {
        if (null == date) return defaultValue;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getStringValue(String value, String defaultValue) {
        return (value == null || "".equals(value) || "0".equals(value) || "-1".equals(value)) ? defaultValue : value;
    }

    public static String getCurrencyValue(String value, String currencySymbol, String defaultValue) {
        return (isEmptyValue(value) || "0".equals(value) || "-1".equals(value)) ? defaultValue : value.concat(currencySymbol);
    }

    public static String getFractionValue(String value, String denominator, String defaultValue) {
        return (isEmptyValue(value) || "0".equals(value) || "-1".equals(value)) ? defaultValue : value.concat("/").concat(denominator);
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
