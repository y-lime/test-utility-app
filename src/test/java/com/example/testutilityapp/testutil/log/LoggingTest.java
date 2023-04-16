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

    @Test
    void ロガーごとに検証() throws Exception {
        org.slf4j.Logger infologger = org.slf4j.LoggerFactory.getLogger("INFO");
        org.slf4j.Logger errorlogger = org.slf4j.LoggerFactory.getLogger("ERROR");

        infologger.info("first message");
        infologger.error("second message");
        errorlogger.info("third message");
        errorlogger.error("fourth message");

        List<ILoggingEvent> infoevents = LoggingEventExtension.getLoggingEvent("INFO");
        Assertions.assertThat(infoevents).hasSize(2);
        Assertions.assertThat(infoevents.get(0).getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
        Assertions.assertThat(infoevents.get(0).getLoggerName()).isEqualTo("INFO");
        Assertions.assertThat(infoevents.get(0).getMessage()).isEqualTo("first message");
        Assertions.assertThat(infoevents.get(1).getLevel()).isEqualTo(ch.qos.logback.classic.Level.ERROR);
        Assertions.assertThat(infoevents.get(1).getLoggerName()).isEqualTo("INFO");
        Assertions.assertThat(infoevents.get(1).getMessage()).isEqualTo("second message");

        List<ILoggingEvent> errorevents = LoggingEventExtension.getLoggingEvent("ERROR");
        Assertions.assertThat(errorevents).hasSize(2);
        Assertions.assertThat(errorevents.get(0).getLevel()).isEqualTo(ch.qos.logback.classic.Level.INFO);
        Assertions.assertThat(errorevents.get(0).getLoggerName()).isEqualTo("ERROR");
        Assertions.assertThat(errorevents.get(0).getMessage()).isEqualTo("third message");
        Assertions.assertThat(errorevents.get(1).getLevel()).isEqualTo(ch.qos.logback.classic.Level.ERROR);
        Assertions.assertThat(errorevents.get(1).getLoggerName()).isEqualTo("ERROR");
        Assertions.assertThat(errorevents.get(1).getMessage()).isEqualTo("fourth message");
    }
}
