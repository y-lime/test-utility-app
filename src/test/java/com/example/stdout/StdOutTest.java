package com.example.stdout;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class StdOutTest {

    private ByteArrayOutputStream _baos;
    private PrintStream _out;

    @BeforeEach
    void setUp() {
        this._baos = new ByteArrayOutputStream();
        this._out = System.out;
        System.setOut(new PrintStream(new BufferedOutputStream(this._baos)));
    }

    @AfterEach
    void tearDown() {
        System.setOut(this._out);
    }

    @Test
    void 標準出力ログの検査() throws Exception {
        org.slf4j.Logger logger = (org.slf4j.Logger) LoggerFactory.getLogger("STDOUT");
        logger.error("ERROR MESSAGE");

        Assertions.assertThat(this._baos.toString()).endsWith("ERROR MESSAGE\n");
    }

}
