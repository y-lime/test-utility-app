package com.example.testutilityapp.component;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.testutilityapp.form.EchoForm;

import jakarta.servlet.http.HttpSession;

// MockMvcを使用するために、@AutoCinfigureMockMvcを使用する
@SpringBootTest
@AutoConfigureMockMvc
class EchoTest {

    @Autowired
    private MockMvc mockMvc;

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
        Assertions.assertThat(echoForm.getText()).isEqualTo("TEST TEXT");
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
        Assertions.assertThat(echoForm.getText()).isEqualTo("12345678901234567890");

    }

}
