package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Captcha;
import com.mychat.mychatserver.mapper.CaptchaMapper;
import com.mychat.mychatserver.service.impl.CaptchaServiceImpl;
import com.mychat.mychatserver.utils.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CaptchaServiceImplTest {

    @MockBean
    private EmailUtils emailUtils;

    @MockBean
    private CaptchaMapper captchaMapper;

    @Autowired
    private CaptchaServiceImpl captchaService;

    //测试成功发送验证码邮件的情况
    @Test
    public void testSendCaptchaByEmail_Success() {
        // 模拟 EmailUtils 的行为
        when(emailUtils.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

        // 准备测试数据
        String email = "test@example.com";

        // 调用被测试的方法
        boolean result = captchaService.sendCaptchaByEmail(email);

        // 验证邮件发送是否被调用
        verify(emailUtils, times(1)).sendEmail(eq(email), eq("验证码"), anyString());

        // 验证验证码实体是否保存到数据库
        verify(captchaMapper, times(1)).save(any(Captcha.class));

        // 断言结果为 true
        assertTrue(result,"测试成功发送验证码邮件的情况测试失败");
    }

    // 测试发送验证码邮件失败的情况
    @Test
    public void testSendCaptchaByEmail_FailureToSendEmail() {
        // 模拟 EmailUtils 的行为来模拟发送邮件失败的情况
        when(emailUtils.sendEmail(anyString(), anyString(), anyString())).thenReturn(false);

        // 准备测试数据
        String email = "test@example.com";

        // 调用被测试的方法
        boolean result = captchaService.sendCaptchaByEmail(email);

        // 验证邮件发送是否被调用
        verify(emailUtils, times(1)).sendEmail(eq(email), eq("验证码"), anyString());

        // 验证验证码实体未保存到数据库
        verify(captchaMapper, never()).save(any(Captcha.class));

        // 断言结果为 false
        assertFalse(result,"测试发送验证码邮件失败的情况测试失败");
    }

    //测试成功匹配验证码的情况
    @Test
    public void testMatchCaptcha_Success() {
        // 模拟 CaptchaMapper 的行为
        when(captchaMapper.matchCaptcha(anyString(), anyString())).thenReturn(true);

        // 准备测试数据
        String email = "test@example.com";
        String code = "123456";

        // 调用被测试的方法
        boolean result = captchaService.matchCaptcha(email, code);

        // 验证 matchCaptcha 方法是否被正确调用
        verify(captchaMapper, times(1)).matchCaptcha(eq(email), eq(code));

        // 验证 markCaptchaStatus 方法是否被正确调用
        verify(captchaMapper, times(1)).markCaptchaStatus(eq(email), eq(true));

        // 断言结果为 true
        assertTrue(result,"测试成功匹配验证码的情况测试失败");
    }

    // 测试匹配验证码失败的情况
    @Test
    public void testMatchCaptcha_Failure() {
        // 模拟 CaptchaMapper 的行为来模拟匹配失败的情况
        when(captchaMapper.matchCaptcha(anyString(), anyString())).thenReturn(false);

        // 准备测试数据
        String email = "test@example.com";
        String code = "123456";

        // 调用被测试的方法
        boolean result = captchaService.matchCaptcha(email, code);

        // 验证 matchCaptcha 方法是否被正确调用
        verify(captchaMapper, times(1)).matchCaptcha(eq(email), eq(code));

        // 验证 markCaptchaStatus 方法未被调用
        verify(captchaMapper, never()).markCaptchaStatus(anyString(), anyBoolean());

        // 断言结果为 false
        assertFalse(result,"测试匹配验证码失败的情况测试失败");
    }
}
