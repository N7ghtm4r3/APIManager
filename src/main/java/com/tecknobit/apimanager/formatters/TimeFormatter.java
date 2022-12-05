package com.tecknobit.apimanager.formatters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Locale.getDefault;

/**
 * The {@code TimeFormatter} class is a useful tool class to format in different ways the {@code "time"}
 *
 * <pre>
 *     {@code
 *
 *         // Change the default pattern (dd/MM/yyyy HH:mm:ss) programmatically
 *         // will overwrite the default and used in all formatting
 *         TimeFormatter.changeDefaultPattern("yyyy/MM/dd HH:mm:ss");
 *
 *         // or pass pattern as argument in all methods to change only in that formatting
 *         String stringDate = TimeFormatter.getStringDate(System.currentTimeMillis(),
 *         "yyyy/MM/dd HH:mm:ss"); // --> 2022/12/05 18:34:37
 *
 *         // string format
 *         String stringDate = TimeFormatter.getStringDate(System.currentTimeMillis());
 *         // --> 2022/12/05 18:34:37
 *
 *         // long format
 *         System.out.println(TimeFormatter.getDateTimestamp(stringDate)); // --> 1670261677000
 *
 *         // date format
 *         // with string as argument
 *         System.out.println(TimeFormatter.getDate(stringDate));
 *         // --> Mon Dec 05 18:34:37 CET 2022
 *
 *         // with long as argument
 *         System.out.println(TimeFormatter.getDate(TimeFormatter.getDateTimestamp(stringDate)));
 *         // --> Mon Dec 05 18:34:37 CET 2022
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 **/
public abstract class TimeFormatter {

    /**
     * {@code dateFormatter} is instance that help to format date
     **/
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", getDefault());

    /**
     * This method is used to get from a timestamp a date formatted by {@code "Locale"}
     *
     * @param timestamp: timestamp of the date to get
     * @return date value as {@link String}
     **/
    public static String getStringDate(long timestamp) {
        return dateFormatter.format(new Date(timestamp));
    }

    /**
     * This method is used to get from a timestamp a date formatted by {@code "Locale"}
     *
     * @param timestamp: timestamp of the date to get
     * @param pattern:   pattern to format return date es. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link String}
     **/
    public static String getStringDate(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }

    /**
     * This method is used to get a date from a date string formatted by {@code "Locale"}
     *
     * @param date: date as {@link String}
     * @return date value as {@link Date}
     **/
    public static Date getDate(String date) {
        return new Date(getDateTimestamp(date));
    }

    /**
     * This method is used to get a date from a date string formatted by {@code "Locale"}
     *
     * @param date:    date as {@link String}
     * @param pattern: pattern to format return date es. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link Date}
     **/
    public static Date getDate(String date, String pattern) {
        return new Date(getDateTimestamp(date, pattern));
    }

    /**
     * This method is used to get a date from a date timestamp
     *
     * @param date: date timestamp as long
     * @return date value as {@link Date}
     **/
    public static Date getDate(long date) {
        return new Date(date);
    }

    /**
     * This method is used to get a date from a date timestamp
     *
     * @param date:    date timestamp as long
     * @param pattern: pattern to format return date es. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link Date}
     **/
    public static Date getDate(long date, String pattern) {
        return new Date(getDateTimestamp(getStringDate(date, pattern), pattern));
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date: date to get timestamp
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public static long getDateTimestamp(Date date) {
        return getDateTimestamp(date.toString());
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date: date to get timestamp
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public static long getDateTimestamp(String date) {
        try {
            return dateFormatter.parse(date).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date:    date to get timestamp
     * @param pattern: pattern to format return date es. dd/MM/yyyy hh:mm:ss
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public static long getDateTimestamp(Date date, String pattern) {
        return getDateTimestamp(date.toString(), pattern);
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date:    date to get timestamp
     * @param pattern: pattern to format return date es. dd/MM/yyyy hh:mm:ss
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public static long getDateTimestamp(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * This method is used to set a new default pattern at {@link #dateFormatter}
     *
     * @param newPattern: new pattern to set at {@link #dateFormatter}
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     **/
    public static void changeDefaultPattern(String newPattern) {
        dateFormatter = new SimpleDateFormat(newPattern, getDefault());
    }

}
