package com.example.testutilityapp.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SampleUtilService {
    public Date getSystemDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public Date getNextWeekDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DATE, 1);
        }
        return cal.getTime();
    }
}
