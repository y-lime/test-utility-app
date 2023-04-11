package com.example.testutilityapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class SampleService {

    @Autowired
    private SampleUtilService sampleUtilService;

    public String getDateStr() {
        Date date = sampleUtilService.getSystemDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    public String getNextWeekDateStr() {
        Date date = sampleUtilService.getNextWeekDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

}
