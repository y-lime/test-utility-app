package com.example.mock;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        when(sampleSubClass.calcAdd(anyInt(), anyInt())).thenReturn(10);
        Assertions.assertThat(sampleClass.calc(1, 2)).isEqualTo(10);
    }

    @Test
    void 例外を出すMock() throws Exception {
        when(sampleSubClass.calcDiv(anyInt(), anyInt())).thenThrow(Exception.class);
        Assertions.assertThat(sampleClass.calcDiv(2, 2)).isEqualTo(-1);
    }

    @Test
    void 動的なMock() throws Exception {
        when(sampleSubClass.concatMessage(anyString())).thenAnswer(new Answer<String>(){
            @Override
            public String answer(InvocationOnMock invocation){
                if(invocation.getArgument(0) != null){
                    return invocation.getArgument(0) + "test-message";
                }
                return "";
            }
        });
        Assertions.assertThat(sampleClass.getSubMessageWithPrefix("info:")).isEqualTo("info:test-message");
    }

    @Test
    void 動的なMock_Lambda() throws Exception {
        when(sampleSubClass.concatMessage(anyString())).thenAnswer(invocation -> {
            if(invocation.getArgument(0) != null){
                return invocation.getArgument(0) + "test-message";
            }
            return "";
        });
        Assertions.assertThat(sampleClass.getSubMessageWithPrefix("info:")).isEqualTo("info:test-message");
    }

    @Test
    void Mockの振る舞いを定義しない() {
        // Mockの振る舞いを定義しなければ、nullが返される
        Assertions.assertThat(sampleClass.getSubMessage()).isNull();
    }

    void voidのMock() throws Exception {
        // TODO あとで実装
    }

    @Test
    void StaticメソッドのMock() throws Exception {
        sampleStaticMethods.when(() -> SampleStaticMethods.calcAdd(anyInt(), anyInt())).thenReturn(10);
        Assertions.assertThat(sampleClass.calcUsingStaticMethod(1, 2)).isEqualTo(10);
    }

}
