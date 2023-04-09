package com.example.testutilityapp.testutil.log;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ch.qos.logback.classic.spi.ILoggingEvent;

@ExtendWith(LoggingEventExtension.class)
class LoggingTest {

    @Test
    void logging() throws Exception {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("slf4j");
        logger.info("INFO: test slf4j.");
        logger.error("ERRPR: test slf4j error.");

        List<ILoggingEvent> events = LoggingEventExtension.getLoggingEvent();
        Assertions.assertThat(events).as("count of log events").hasSize(2);
        Assertions.assertThat(events.get(0).getLevel()).as("log level").isEqualTo(ch.qos.logback.classic.Level.INFO);
        Assertions.assertThat(events.get(0).getLoggerName()).as("logger name").isEqualTo("slf4j");
        Assertions.assertThat(events.get(0).getMessage()).as("log message").isEqualTo("INFO: test slf4j.");
        Assertions.assertThat(events.get(1).getLevel()).as("log level").isEqualTo(ch.qos.logback.classic.Level.ERROR);
        Assertions.assertThat(events.get(1).getLoggerName()).as("logger name").isEqualTo("slf4j");
        Assertions.assertThat(events.get(1).getMessage()).as("log message").isEqualTo("ERRPR: test slf4j error.");

    }

}
