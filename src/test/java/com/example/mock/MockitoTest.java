package com.example.mock;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.example.mock.sample.SampleClass;
import com.example.mock.sample.SampleStaticMethods;
import com.example.mock.sample.SampleSubClass;

// テストクラスのインスタンスフィールドで@InjectMocks、@Mockを使用する場合は、@ExtendWithは必須
@ExtendWith(MockitoExtension.class)
class MockitoTest {

    @Mock
    private SampleSubClass sampleSubClass;

    // staticメソッドをモック化したい時は、mockito-inlineのMockedStaticを使用する
    @Mock
    private MockedStatic<SampleStaticMethods> sampleStaticMethods;

    // Mockを注入したいクラスには@InjectMocksを使用する
    // @InjectMocksを付与したフィールドは自動的にnewされる
    @InjectMocks
    private SampleClass sampleClass;

    @Test
    void 引数なしMock() throws Exception {
        when(sampleSubClass.getX()).thenReturn(99);
        Assertions.assertThat(sampleClass.getSubX()).isEqualTo(99);
    }

    @Test
    void 引数ありMock() throws Exception {
        // 引数ありのMockはArgumentCpatorを使用してMockの使用を検証する
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yCaptor = ArgumentCaptor.forClass(Integer.class);

        when(sampleSubClass.calcAdd(anyInt(), anyInt())).thenReturn(10);
        Assertions.assertThat(sampleClass.calc(1, 2)).isEqualTo(10);

        verify(sampleSubClass, times(1)).calcAdd(xCaptor.capture(), yCaptor.capture());
        Assertions.assertThat(xCaptor.getValue()).isEqualTo(1);
        Assertions.assertThat(yCaptor.getValue()).isEqualTo(2);
    }

    @Test
    void 例外を出すMock() throws Exception {
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yCaptor = ArgumentCaptor.forClass(Integer.class);

        when(sampleSubClass.calcDiv(anyInt(), anyInt())).thenThrow(Exception.class);
        Assertions.assertThat(sampleClass.calcDiv(2, 2)).isEqualTo(-1);

        verify(sampleSubClass, times(1)).calcDiv(xCaptor.capture(), yCaptor.capture());
        Assertions.assertThat(xCaptor.getValue()).isEqualTo(2);
        Assertions.assertThat(yCaptor.getValue()).isEqualTo(2);
    }

    @Test
    void 動的なMock() throws Exception {
        ArgumentCaptor<String> prefixCaptor = ArgumentCaptor.forClass(String.class);

        when(sampleSubClass.concatMessage(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) {
                if (invocation.getArgument(0) != null) {
                    return invocation.getArgument(0) + "test-message";
                }
                return "";
            }
        });
        Assertions.assertThat(sampleClass.getSubMessageWithPrefix("info:")).isEqualTo("INFO:test-message");

        verify(sampleSubClass, times(1)).concatMessage(prefixCaptor.capture());
        Assertions.assertThat(prefixCaptor.getValue()).isEqualTo("INFO:");
    }

    @Test
    void 動的なMock_Lambda() throws Exception {
        ArgumentCaptor<String> prefixCaptor = ArgumentCaptor.forClass(String.class);

        when(sampleSubClass.concatMessage(anyString())).thenAnswer(invocation -> {
            if (invocation.getArgument(0) != null) {
                return invocation.getArgument(0) + "test-message";
            }
            return "";
        });
        Assertions.assertThat(sampleClass.getSubMessageWithPrefix("info:")).isEqualTo("INFO:test-message");

        verify(sampleSubClass, times(1)).concatMessage(prefixCaptor.capture());
        Assertions.assertThat(prefixCaptor.getValue()).isEqualTo("INFO:");
    }

    @Test
    void Mockの振る舞いを定義しない() throws Exception {
        // Mockの振る舞いを定義しなければ、nullが返される
        Assertions.assertThat(sampleClass.getSubMessage()).isNull();
    }

    @Test
    void voidのMock() throws Exception {
        ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(sampleSubClass).setX(anyInt());
        sampleClass.initSubX();
        verify(sampleSubClass, times(1)).setX(intCaptor.capture());
        Assertions.assertThat(intCaptor.getValue()).isEqualTo(10);
    }

    @Test
    void StaticメソッドのMock() throws Exception {
        ArgumentCaptor<Integer> xCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yCaptor = ArgumentCaptor.forClass(Integer.class);

        sampleStaticMethods.when(() -> SampleStaticMethods.calcAdd(anyInt(), anyInt())).thenReturn(10);
        Assertions.assertThat(sampleClass.calcUsingStaticMethod(1, 2)).isEqualTo(10);

        // MockedStaticのverifyは書き方がちょっと変わる
        sampleStaticMethods.verify(() -> SampleStaticMethods.calcAdd(xCaptor.capture(), yCaptor.capture()));
        Assertions.assertThat(xCaptor.getValue()).isEqualTo(1);
        Assertions.assertThat(yCaptor.getValue()).isEqualTo(2);
    }
}
