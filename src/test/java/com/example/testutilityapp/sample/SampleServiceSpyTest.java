package com.example.testutilityapp.sample;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.testutilityapp.service.SampleService;
import com.example.testutilityapp.service.SampleUtilService;

@SpringBootTest
class SampleServiceSpyTest {

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private SampleUtilService sampleUtilService;

    @Autowired
    private SampleService sampleService;

    @Test
    void BeanのSpy() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = sdf.parse("2023-04-01 00:00:00.000");
        when(sampleUtilService.getNextWeekDate()).thenReturn(date);

        String nextdatestr = sampleService.getNextWeekDateStr();
        Assertions.assertThat(nextdatestr).isEqualTo("2023/04/01");
        verify(sampleUtilService, times(1)).getNextWeekDate();

        Date today = new Date();
        SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy/MM/dd");
        String datestr = sampleService.getDateStr();
        Assertions.assertThat(datestr).isEqualTo(sdfShort.format(today));
    }
}
