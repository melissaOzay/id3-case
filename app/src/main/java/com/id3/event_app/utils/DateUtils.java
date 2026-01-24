package com.id3.event_app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class DateUtils {

    private DateUtils() {
    }

    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(date);

        return today.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR);
    }

    public static String getRelativeTime(Date date) {
        if (date == null) {
            return "";
        }

        long diff = System.currentTimeMillis() - date.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long days = TimeUnit.MILLISECONDS.toDays(diff);

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + "m ago";
        } else if (hours < 24) {
            return hours + "h ago";
        } else if (days < 7) {
            return days + "d ago";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            return sdf.format(date);
        }
    }

    public static String formatEventDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        return sdf.format(date).toUpperCase(Locale.ENGLISH);
    }
}
