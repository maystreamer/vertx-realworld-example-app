package io.greyseal.realworld.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static final String dateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss.SSS z";

    public static long getTimeInMS() {
        return new Date().getTime();
    }

    public static long getTimeInMS(Date dt) {
        return dt.getTime();
    }

    public static long dateDiff(final Date endDate, final Date startDate) {
        return (endDate.getTime() - startDate.getTime());
    }

    public static long dateDiff(final String endDate, final String startDate) {
        return (getTimeInMS(getDate(endDate)) - getTimeInMS(getDate(startDate)));
    }

    public static Date getDateFromMS(final long timeInMS) {
        return new Date(timeInMS);
    }

    public static String formatDate(final Date dt) {
        final DateFormat df = new SimpleDateFormat(dateTimeFormatPattern);
        return df.format(dt);
    }

    public static Date getDate(final String date) {
        try {
            return new SimpleDateFormat(dateTimeFormatPattern).parse(date);
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return null;
    }

    public static String toISO8601UTC(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(dateTimeFormatPattern);
        df.setTimeZone(tz);
        return df.format(date);
    }

    public static Date fromISO8601UTC(String dateStr) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(dateTimeFormatPattern);
        df.setTimeZone(tz);
        try {
            return df.parse(dateStr);
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return null;
    }
}