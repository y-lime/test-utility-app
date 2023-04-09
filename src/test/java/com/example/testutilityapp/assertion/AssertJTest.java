package com.example.testutilityapp.assertion;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AssertJTest {

    @Test
    void Trueの検証() throws Exception {
        boolean flag = true;
        Assertions.assertThat(flag).isTrue();
    }

    @Test
    void Falseの検証() throws Exception {
        boolean flag = false;
        Assertions.assertThat(flag).isFalse();
    }

    @Test
    void Nullの検証() throws Exception {
        String actual = null;
        Assertions.assertThat(actual).isNull();
    }

    @Test
    void NotNullの検証() throws Exception {
        String actual = "test";
        Assertions.assertThat(actual).isNotNull();
    }

    @Test
    void 空白の検証() throws Exception {
        String actual = "";
        Assertions.assertThat(actual).isBlank();

        String space = " ";
        Assertions.assertThat(space).isBlank();

    }

    @Test
    void 空白でない検証() throws Exception {
        String actual = "test";
        Assertions.assertThat(actual).isNotBlank();
    }

    @Test
    void 空文字の検証() throws Exception {
        String actual = "";
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void 空文字でない検証() throws Exception {
        String actual = "test";
        String blank = " ";

        Assertions.assertThat(actual).isNotEmpty();
        Assertions.assertThat(blank).isNotEmpty();
    }

    @Test
    void NullOr空文字の検証() throws Exception {
        String empty = "";
        String nullstr = null;

        Assertions.assertThat(empty).isNullOrEmpty();
        Assertions.assertThat(nullstr).isNullOrEmpty();
    }

    @Test
    void 一致の検証() throws Exception {
        String expected = "test";
        String actual = "test";
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 不一致の検証() throws Exception {
        String expected = "TEST";
        String actual = "test";
        Assertions.assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void 先頭文字列の検証() throws Exception {
        String expected = "tes";
        String actual = "test";
        Assertions.assertThat(actual).startsWith(expected);
    }

    @Test
    void 末尾文字列の検証() throws Exception {
        String expected = "st";
        String actual = "test";
        Assertions.assertThat(actual).endsWith(expected);
    }

    @Test
    void 大文字小文字を無視した一致の検証() throws Exception {
        String expected = "test";
        String actual = "TEST";
        Assertions.assertThat(actual).isNotEqualTo(expected);
        Assertions.assertThat(actual).isEqualToIgnoringCase(expected);
    }

    @Test
    void 例外の検証() throws Exception {
        Throwable ex = Assertions.catchThrowable(() -> {
            throw new ClassNotFoundException("ERROR");
        });
        Assertions.assertThat(ex).isInstanceOf(Exception.class);
        Assertions.assertThat(ex).isInstanceOf(ClassNotFoundException.class);
        Assertions.assertThat(ex).hasMessage("ERROR");
    }

    @Test
    void Collectionの検証() throws Exception {
        List<String> emptyList = new ArrayList<>();
        Assertions.assertThat(emptyList).isEmpty();

        List<String> actual = new ArrayList<>();
        actual.add("first item");
        actual.add("second item");
        Assertions.assertThat(actual).hasSize(2);
        Assertions.assertThat("first item");
    }

}
