package com.example.testutilityapp.sample;

import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.testutilityapp.service.SampleService;
import com.example.testutilityapp.service.SampleUtilService;

@SpringBootTest
class SampleServiceMockTest {

    @MockBean
    private SampleUtilService sampleUtilService;

    @Autowired
    private SampleService sampleService;

    @Test
    void ServiceのMock() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = sdf.parse("2023-04-01 00:00:00.000");
        when(sampleUtilService.getNextWeekDate()).thenReturn(date);

        String nextdatestr = sampleService.getNextWeekDateStr();
        Assertions.assertThat(nextdatestr).isEqualTo("2023/04/01");

        // Mock定義をしていないため、nullが返され、NullPointerException発生
        Throwable ex = Assertions.catchThrowable(() -> {
            sampleService.getDateStr();
        });
        Assertions.assertThat(ex).isInstanceOf(NullPointerException.class);
    }
}
