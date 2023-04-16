package com.example.testutilityapp.rest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.testutilityapp.form.EchoForm;
import com.example.testutilityapp.service.TranslateCaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EchoRestController.class)
public class EchoRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @MockBean
    private TranslateCaseService translateCaseService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    void setUp(){
        when(translateCaseService.execute(anyString())).thenReturn("anyString");
    }

    @Test
    void GETメソッドのテスト() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/echo/upper").param("input", "testtext"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("anyString"));

        verify(translateCaseService, times(1)).execute(stringCaptor.capture());
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo("testtext");
    }

    @Test
    void POSTメソッドのテスト() throws Exception {
        EchoForm echoForm = new EchoForm();
        echoForm.setText("test text");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/echo/upper").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(echoForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("anyString"));

        verify(translateCaseService, times(1)).execute(stringCaptor.capture());
        Assertions.assertThat(stringCaptor.getValue()).isEqualTo("test text");
    }

    @Test
    void POSTメソッドのテスト入力値最大文字数超過() throws Exception {
        EchoForm echoForm = new EchoForm();
        echoForm.setText("123456789012345678901");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/echo/upper").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(echoForm)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
