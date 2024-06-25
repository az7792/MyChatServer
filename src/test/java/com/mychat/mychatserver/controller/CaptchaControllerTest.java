package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.service.CaptchaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CaptchaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaptchaService captchaService;

    // 测试发送验证码成功的情况
    @Test
    public void testSendCaptchaByEmail_Success() throws Exception {
        String email = "test@example.com";

        // 模拟验证码发送成功
        when(captchaService.sendCaptchaByEmail(email)).thenReturn(true);

        // 发起 POST 请求，发送验证码
        mockMvc.perform(MockMvcRequestBuilders.post("/sendCaptchaByEmail")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试发送验证码失败的情况
    @Test
    public void testSendCaptchaByEmail_Failure() throws Exception {
        String email = "test@example.com";

        // 模拟验证码发送失败
        when(captchaService.sendCaptchaByEmail(email)).thenReturn(false);

        // 发起 POST 请求，发送验证码
        mockMvc.perform(MockMvcRequestBuilders.post("/sendCaptchaByEmail")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试匹配验证码成功的情况
    @Test
    public void testMatchCaptcha_Success() throws Exception {
        String email = "test@example.com";
        String code = "123456";

        // 模拟验证码匹配成功
        when(captchaService.matchCaptcha(email, code)).thenReturn(true);

        // 发起 POST 请求，匹配验证码
        mockMvc.perform(MockMvcRequestBuilders.post("/matchCaptcha")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email)
                        .param("code", code))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试匹配验证码失败的情况
    @Test
    public void testMatchCaptcha_Failure() throws Exception {
        String email = "test@example.com";
        String code = "123456";

        // 模拟验证码匹配失败
        when(captchaService.matchCaptcha(email, code)).thenReturn(false);

        // 发起 POST 请求，匹配验证码
        mockMvc.perform(MockMvcRequestBuilders.post("/matchCaptcha")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", email)
                        .param("code", code))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }
}
