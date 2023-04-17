package com.example.testutilityapp.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.testutilityapp.sample.SamplePrivateClass;

class ReflectionTest {

    @BeforeEach
    void setUp() {
        SamplePrivateClass.clear();
    }

    @Test
    void testStaticField() {
        Assertions.assertThat(ReflectionTestUtils.getField(SamplePrivateClass.class, "staticField")).isNull();
        // private staticフィールドに値を設定する
        ReflectionTestUtils.setField(SamplePrivateClass.class, "staticField", "A");
        Assertions.assertThat(ReflectionTestUtils.getField(SamplePrivateClass.class, "staticField")).isEqualTo("A");
    }

    @Test
    void testInstanceField() {
        SamplePrivateClass samplePrivateObj = new SamplePrivateClass();
        Assertions.assertThat(ReflectionTestUtils.getField(samplePrivateObj, "instanceField")).isNull();
        // private staticフィールドに値を設定する
        ReflectionTestUtils.setField(samplePrivateObj, "instanceField", "A");
        Assertions.assertThat(ReflectionTestUtils.getField(samplePrivateObj, "instanceField")).isEqualTo("A");

    }

    @Test
    void tedtInvokeStaticMethod() {
        ReflectionTestUtils.setField(SamplePrivateClass.class, "staticField", "A");
        Assertions
                .assertThat(
                        (String) ReflectionTestUtils.invokeMethod(SamplePrivateClass.class, "invokeStaticMethod", "B"))
                .isEqualTo("A:B");
    }

    @Test
    void tedtInvokeInstanceMethod() {
        SamplePrivateClass samplePrivateObj = new SamplePrivateClass();

        ReflectionTestUtils.setField(samplePrivateObj, "instanceField", "A");
        Assertions
                .assertThat(
                        (String) ReflectionTestUtils.invokeMethod(samplePrivateObj, "invokeInstanceMethod", "B"))
                .isEqualTo("A:B");
    }

}
