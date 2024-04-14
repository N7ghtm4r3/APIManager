package com.tecknobit.apimanager.formatters;

import com.tecknobit.apimanager.annotations.Wrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Locale.getDefault;

/**
 * The {@code TimeFormatter} class is a useful tool class to format in different ways the {@code "time"}
 *
 * <pre>
 *     {@code
 *
 *         // Get an instance of the TimeFormatter
 *         TimeFormatter timeFormatter = TimeFormatter.getInstance();
 *
 *         // Change the values of the formatter
 *         timeFormatter.changeFormatter("new_pattern", new_locale);
 *
 *         // or pass pattern as argument in all methods to change only in that formatting
 *         String stringDate = timeFormatter.formatNowAsString("yyyy/MM/dd HH:mm:ss"); // --> 2024/04/14 13:46:30
 *
 *         // string format
 *         String stringDate = timeFormatter.formatAsString(time_in_millis); // --> 2024/04/14 13:46:30
 *
 *         // long format
 *         System.out.println(timeFormatter.formatAsTimestamp("string_date")); // --> 1670261677000
 *
 *         // date format
 *         // with string as argument
 *         System.out.println(timeFormatter.formatAsDate("string_date")); // --> Sun Apr 14 13:46:30 CET 2024
 *
 *         // with long as argument
 *         System.out.println(timeFormatter.formatAsDate(time_in_millis); // --> Sun Apr 14 13:46:30 CET 2024
 *     }
 * </pre>
 *
 * @author N7ghtm4r3 - Tecknobit
 *
 */
public class TimeFormatter {

    /**
     * {@code DEFAULT_PATTERN} the default pattern used for the {@link #formatter}
     */
    public static final String DEFAULT_PATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * {@code dateFormatter} is instance that help to format date
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", getDefault());

    /**
     * {@code formatter} the formatter used to format the values
     */
    private SimpleDateFormat formatter;

    /**
     * {@code pattern} the pattern for the {@link #formatter}
     */
    private String pattern;

    /**
     * {@code locale} the locale for the {@link #formatter}
     */
    private Locale locale;

    /**
     * Constructor to init the {@link TimeFormatter}
     * <p>
     * No-any params required
     */
    private TimeFormatter() {
        this(DEFAULT_PATTERN, getDefault());
    }

    /**
     * Constructor to init the {@link TimeFormatter}
     *
     * @param pattern: the pattern for the {@link #formatter}
     */
    private TimeFormatter(String pattern) {
        this(pattern, getDefault());
    }

    /**
     * Constructor to init the {@link TimeFormatter}
     *
     * @param locale: the locale for the {@link #formatter}
     */
    private TimeFormatter(Locale locale) {
        this(DEFAULT_PATTERN, locale);
    }

    /**
     * Constructor to init the {@link TimeFormatter}
     *
     * @param pattern: the pattern for the {@link #formatter}
     * @param locale:  the locale for the {@link #formatter}
     */
    private TimeFormatter(String pattern, Locale locale) {
        formatter = new SimpleDateFormat(pattern, getDefault());
        this.pattern = pattern;
        this.locale = locale;
    }

    /**
     * This method is used to format the current timestamp as string <br>
     * No-any params required
     *
     * @return the current timestamp value formatted as {@link String}
     *
     */
    @Wrapper
    public String formatNowAsString() {
        return formatAsString(System.currentTimeMillis());
    }

    /**
     * This method is used to format a timestamp as string
     *
     * @param timestamp: the timestamp to format
     * @return the timestamp value formatted as {@link String}
     */
    public String formatAsString(long timestamp) {
        return formatter.format(new Date(timestamp));
    }

    /**
     * This method is used to format the current timestamp as string
     *
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     * @return the current timestamp value formatted as {@link String}
     */
    @Wrapper
    public String formatAsNowString(String pattern) {
        return formatAsString(System.currentTimeMillis(), pattern);
    }

    /**
     * This method is used to format a timestamp as string
     *
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     *
     * @return the timestamp value formatted as {@link String}
     *
     */
    public String formatAsString(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, locale).format(new Date(timestamp));
    }

    /**
     * This method is used to format the current timestamp as a date <br>
     *
     * No-any params required
     *
     * @return the current timestamp value as {@link Date}
     */
    @Wrapper
    public Date formatNowAsDate() {
        return formatAsDate(System.currentTimeMillis());
    }

    /**
     * This method is used to format a string-date as date
     *
     * @param date: the string-date value to format
     * @return the string-date value formatted as {@link Date}
     */
    public Date formatAsDate(String date) {
        return new Date(formatAsTimestamp(date));
    }

    /**
     * This method is used to format the current timestamp as a date
     *
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     *
     * @return the current timestamp value as {@link Date}
     */
    @Wrapper
    public Date formatNowAsDate(String pattern) {
        return formatAsDate(System.currentTimeMillis(), pattern);
    }

    /**
     * This method is used to format a string-date as date
     *
     * @param date:    the string-date value to format
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     * @return the string-date value formatted as {@link Date}
     */
    public Date formatAsDate(String date, String pattern) {
        return new Date(formatAsTimestamp(date, pattern));
    }

    /**
     * This method is used to format a timestamp value as date
     *
     * @param date: the timestamp value to format
     *
     * @return the timestamp value formatted as {@link Date}
     */
    public Date formatAsDate(long date) {
        return new Date(date);
    }

    /**
     * This method is used to format a timestamp value as date
     *
     * @param date: the timestamp value to format
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     *
     * @return the timestamp value formatted as {@link Date}
     */
    public Date formatAsDate(long date, String pattern) {
        return new Date(formatAsTimestamp(formatAsString(date), pattern));
    }

    /**
     * This method is used to format the current instant as timestamp <br>
     * No-any params required
     *
     * @return the current instant value formatted as long
     *
     */
    public long formatNowAsTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * This method is used to format a date value as timestamp
     *
     * @param date: the date value to format
     * @return the date value formatted as long
     */
    public long formatAsTimestamp(Date date) {
        return formatAsTimestamp(date.toString());
    }

    /**
     * This method is used to format a string-date value as timestamp
     *
     * @param date: the string-date value to format
     *
     * @return the string-date value formatted as long
     *
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     */
    public long formatAsTimestamp(String date) {
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * This method is used to format a date value as timestamp
     *
     * @param date: the date value to format
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     *
     * @return the date value formatted as long
     *
     */
    public long formatAsTimestamp(Date date, String pattern) {
        return formatAsTimestamp(date.toString(), pattern);
    }

    /**
     * This method is used to format a string-date value as timestamp
     *
     * @param date:    the string-date value to format
     * @param pattern: pattern to format the return date e.g. dd/MM/yyyy hh:mm:ss
     * @return the string-date value formatted as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     */
    public long formatAsTimestamp(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern, locale).parse(date).getTime();
        } catch (ParseException e) {
            return -1;
        }
    }

    /**
     * This method is used to set a new pattern for the {@link #formatter} instance
     *
     * @param newPattern: the new pattern to set
     */
    public void changePattern(String newPattern) {
        formatter = new SimpleDateFormat(newPattern, locale);
        this.pattern = newPattern;
    }

    /**
     * This method is used to set a new locale for the {@link #formatter} instance
     *
     * @param newLocale: the new locale to set
     */
    public void changeLocale(Locale newLocale) {
        formatter = new SimpleDateFormat(pattern, newLocale);
        this.locale = newLocale;
    }

    /**
     * This method is used to set a new pattern for the {@link #formatter} instance
     *
     * @param newPattern: the new pattern to set
     * @param newLocale:  the new locale to set
     */
    public void changeFormatter(String newPattern, Locale newLocale) {
        formatter = new SimpleDateFormat(newPattern, newLocale);
        this.pattern = newPattern;
        this.locale = newLocale;
    }

    /**
     * Method to get an instance of {@link TimeFormatter} <br>
     * <p>
     * No-any params required
     *
     * @return a time formatter instance with the default values as {@link TimeFormatter}
     */
    public static TimeFormatter getInstance() {
        return new TimeFormatter();
    }

    /**
     * Method to get an instance of {@link TimeFormatter}
     *
     * @param pattern: the pattern for the {@link #formatter}
     * @return a time formatter instance with the default values as {@link TimeFormatter}
     */
    public static TimeFormatter getInstance(String pattern) {
        return new TimeFormatter(pattern);
    }

    /**
     * Method to get an instance of {@link TimeFormatter}
     *
     * @param locale: the locale for the {@link #formatter}
     * @return a time formatter instance with the default values as {@link TimeFormatter}
     */
    public static TimeFormatter getInstance(Locale locale) {
        return new TimeFormatter(locale);
    }

    /**
     * Method to get an instance of {@link TimeFormatter}
     *
     * @param pattern: the pattern for the {@link #formatter}
     * @param locale:  the locale for the {@link #formatter}
     * @return a time formatter instance with the default values as {@link TimeFormatter}
     */
    public static TimeFormatter getInstance(String pattern, Locale locale) {
        return new TimeFormatter(pattern, locale);
    }

    /**
     * This method is used to get from a timestamp a date formatted by {@code "Locale"}
     *
     * @param timestamp: timestamp of the date to get
     * @return date value as {@link String}
     * @deprecated this method is deprecated use instead {@link #formatAsString(long)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static String getStringDate(long timestamp) {
        return dateFormatter.format(new Date(timestamp));
    }

    /**
     * This method is used to get from a timestamp a date formatted by {@code "Locale"}
     *
     * @param timestamp: timestamp of the date to get
     * @param pattern:   pattern to format return date e.g. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link String}
     * @deprecated this method is deprecated use instead {@link #formatAsString(long, String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static String getStringDate(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }

    /**
     * This method is used to get a date from a date string formatted by {@code "Locale"}
     *
     * @param date: date as {@link String}
     * @return date value as {@link Date}
     * @deprecated this method is deprecated use instead {@link #formatAsDate(long)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static Date getDate(String date) {
        return new Date(getDateTimestamp(date));
    }

    /**
     * This method is used to get a date from a date string formatted by {@code "Locale"}
     *
     * @param date:    date as {@link String}
     * @param pattern: pattern to format return date e.g. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link Date}
     * @deprecated this method is deprecated use instead {@link #formatAsDate(long, String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static Date getDate(String date, String pattern) {
        return new Date(getDateTimestamp(date, pattern));
    }

    /**
     * This method is used to get a date from a date timestamp
     *
     * @param date: date timestamp as long
     * @return date value as {@link Date}
     * @deprecated this method is deprecated use instead {@link #formatAsDate(long)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static Date getDate(long date) {
        return new Date(date);
    }

    /**
     * This method is used to get a date from a date timestamp
     *
     * @param date:    date timestamp as long
     * @param pattern: pattern to format return date e.g. dd/MM/yyyy hh:mm:ss
     * @return date value as {@link Date}
     * @deprecated this method is deprecated use instead {@link #formatAsDate(long, String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static Date getDate(long date, String pattern) {
        return new Date(getDateTimestamp(getStringDate(date, pattern), pattern));
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date: date to get timestamp
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     * @deprecated this method is deprecated use instead {@link #formatAsTimestamp(Date)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static long getDateTimestamp(Date date) {
        return getDateTimestamp(date.toString());
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date: date to get timestamp
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     * @deprecated this method is deprecated use instead {@link #formatAsTimestamp(String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
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
     * @param pattern: pattern to format return date e.g. dd/MM/yyyy hh:mm:ss
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     *
     * @deprecated this method is deprecated use instead {@link #formatAsTimestamp(Date, String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static long getDateTimestamp(Date date, String pattern) {
        return getDateTimestamp(date.toString(), pattern);
    }

    /**
     * This method is used to get from a date its timestamp value
     *
     * @param date:    date to get timestamp
     * @param pattern: pattern to format return date e.g. dd/MM/yyyy hh:mm:ss
     * @return date timestamp value as long
     * @implNote when {@link ParseException} has been thrown return value will be -1 as default
     *
     * @deprecated this method is deprecated use instead {@link #formatAsTimestamp(String, String)}
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
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
     */
    @Deprecated(since = "2.2.3", forRemoval = true)
    public static void changeDefaultPattern(String newPattern) {
        dateFormatter = new SimpleDateFormat(newPattern, getDefault());
    }

}
