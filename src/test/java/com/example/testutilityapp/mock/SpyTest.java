package com.example.testutilityapp.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.testutilityapp.sample.SampleClass;
import com.example.testutilityapp.sample.SampleStaticMethods;
import com.example.testutilityapp.sample.SampleSubClass;

@ExtendWith(MockitoExtension.class)
class SpyTest {

    @Spy
    private SampleSubClass sampleSubClass;

    // MockedStaticは@Spyを使用できないため、@Mock(answer = Answers.CALLS_REAL_METHODS)を使用
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private MockedStatic<SampleStaticMethods> sampleStaticMethods;

    @InjectMocks
    private SampleClass sampleClass;

    @Test
    void Spyのテスト() throws Exception {
        when(sampleSubClass.calcAdd(anyInt(), anyInt())).thenReturn(10);
        
        int addResult = sampleClass.calcAdd(2, 1);
        // Mockで定義された振る舞い
        assertThat(addResult).isEqualTo(10);
        ArgumentCaptor<Integer> xAddCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yAddCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(sampleSubClass, times(1)).calcAdd(xAddCaptor.capture(), yAddCaptor.capture());
        assertThat(xAddCaptor.getValue()).isEqualTo(2);
        assertThat(yAddCaptor.getValue()).isEqualTo(1);

        int subResult = sampleClass.calcSub(2, 1);
        // 本物の振る舞い
        assertThat(subResult).isEqualTo(1);
        ArgumentCaptor<Integer> xSubCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> ySubCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(sampleSubClass, times(1)).calcSub(xSubCaptor.capture(), ySubCaptor.capture());
        assertThat(xSubCaptor.getValue()).isEqualTo(2);
        assertThat(ySubCaptor.getValue()).isEqualTo(1);
    }

    @Test
    void StaticメソッドのSpy() throws Exception {

        sampleStaticMethods.when(() -> SampleStaticMethods.calcAdd(anyInt(), anyInt())).thenReturn(10);

        assertThat(sampleClass.calcAddUsingStaticMethod(2, 1)).isEqualTo(10);
        ArgumentCaptor<Integer> xAddCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yAddCaptor = ArgumentCaptor.forClass(Integer.class);
        sampleStaticMethods.verify(() -> SampleStaticMethods.calcAdd(xAddCaptor.capture(), yAddCaptor.capture()));
        assertThat(xAddCaptor.getValue()).isEqualTo(2);
        assertThat(yAddCaptor.getValue()).isEqualTo(1);

        assertThat(sampleClass.calcSubUsingStaticMethod(2, 1)).isEqualTo(1);
        ArgumentCaptor<Integer> xSubCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> ySubCaptor = ArgumentCaptor.forClass(Integer.class);
        sampleStaticMethods.verify(() -> SampleStaticMethods.calcSub(xSubCaptor.capture(), ySubCaptor.capture()));
        assertThat(xSubCaptor.getValue()).isEqualTo(2);
        assertThat(ySubCaptor.getValue()).isEqualTo(1);
    }

}
