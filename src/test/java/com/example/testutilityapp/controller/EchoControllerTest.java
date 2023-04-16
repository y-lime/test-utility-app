package com.example.testutilityapp.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.testutilityapp.form.EchoForm;
import com.example.testutilityapp.service.TranslateCaseService;

import jakarta.servlet.http.HttpSession;

// SpringMvc コントローラのテストには@WebMvcTestアノテーションを使用する
// 単一のコントローラに限定してテストする
@WebMvcTest(controllers = EchoController.class)
class EchoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslateCaseService translateCaseService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void 画面表示() throws Exception {
        MvcResult results = this.mockMvc.perform(MockMvcRequestBuilders.get("/echo"))
                // リターンコードコードは200
                .andExpect(MockMvcResultMatchers.status().isOk())
                // echo/inputへ画面遷移
                .andExpect(MockMvcResultMatchers.view().name("echo/input"))
                // modelにエラーは登録されていない
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                // echoFormが登録されている
                .andExpect(MockMvcResultMatchers.model().attributeExists("echoForm"))
                .andReturn();
        // セッションは設定していない
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        Assertions.assertThat(afterSession).isNull();
    }

    @Test
    void echo正常系() throws Exception {
        when(translateCaseService.execute(anyString())).thenReturn("anyString");

        MvcResult results = this.mockMvc.perform(MockMvcRequestBuilders.post("/echo").param("text", "test text"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("echo/output"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("echoForm"))
                .andReturn();
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        Assertions.assertThat(afterSession).isNull();

        EchoForm echoForm = (EchoForm) results.getRequest().getAttribute("echoForm");
        // TranslateCaseServiceの返り値
        Assertions.assertThat(echoForm.getText()).isEqualTo("anyString");

        verify(translateCaseService, times(1)).execute(stringCaptor.capture());
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo("test text");
    }

    @Test
    void echo入力テキストが空文字() throws Exception {
        MvcResult results = this.mockMvc.perform(MockMvcRequestBuilders.post("/echo").param("text", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("echo/input"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(1))
                // BeanValidationのエラー結果がフォームに格納
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("echoForm", "text", "NotEmpty"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("echoForm"))
                .andReturn();
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        Assertions.assertThat(afterSession).isNull();
    }

    @Test
    void echo入力テキストが最大文字数超過() throws Exception {
        MvcResult results = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/echo").param("text", "123456789012345678901"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("echo/input"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().errorCount(1))
                // BeanValidationのエラー結果がフォームに格納
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrorCode("echoForm", "text", "Size"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("echoForm"))
                .andReturn();
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        Assertions.assertThat(afterSession).isNull();
    }

    @Test
    void echo入力テキストが最大文字数() throws Exception {
        when(translateCaseService.execute(anyString())).thenReturn("anyString");

        MvcResult results = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/echo").param("text", "12345678901234567890"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("echo/output"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("echoForm"))
                .andReturn();
        MockHttpServletRequest returnReq = results.getRequest();
        HttpSession afterSession = returnReq.getSession(false);
        Assertions.assertThat(afterSession).isNull();
        EchoForm echoForm = (EchoForm) results.getRequest().getAttribute("echoForm");
        Assertions.assertThat(echoForm.getText()).isEqualTo("anyString");

        verify(translateCaseService, times(1)).execute(stringCaptor.capture());
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo("12345678901234567890");
    }

}
