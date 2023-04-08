package com.example.mock.sample;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getSystemDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getNextWeekDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DATE, 1);
        }
        return cal.getTime();
    }
}
