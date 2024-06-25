package com.mychat.mychatserver.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailUtilsTest {

    @Autowired
    private EmailUtils emailUtils;

    @MockBean
    private JavaMailSender emailSender;

    @Test
    public void testSendEmail() {
        String to = "18927539687@qq.com";
        String subject = "验证";
        String text = "你的验证码是:123456"; // 固定验证码内容

        // 模拟 emailSender 的行为，不实际发送邮件
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        boolean result = emailUtils.sendEmail(to, subject, text);
        assertTrue(result, "发送失败");
    }
}
