package com.id3.event_app.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void isToday_withNull_returnsFalse() {
        boolean result = DateUtils.isToday(null);

        assertFalse(result);
    }

    @Test
    public void isToday_withCurrentDate_returnsTrue() {
        Date today = new Date();

        boolean result = DateUtils.isToday(today);

        assertTrue(result);
    }

    @Test
    public void formatEventDate_withValidDate_returnsUppercaseFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Date date = calendar.getTime();

        String result = DateUtils.formatEventDate(date);

        assertEquals("MAR 15", result);
    }
}
