package com.springboot.dietapplication.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateFormatter {

    private static DateFormatter instance;

    private final DateFormat iso8601Formatter;

    private DateFormatter() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        iso8601Formatter = df;
    }

    public static DateFormatter getInstance() {
        if (instance == null) {
            instance = new DateFormatter();
        }
        return instance;
    }

    public String getCurrentDate() {
        return iso8601Formatter.format(new Date());
    }
}
