package com.mychat.mychatserver.utils;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptchaUtilsTest {
    @Test
    public void testGenerateCaptcha() {
        for (int i = 0; i < 100; i++) {
            String result = CaptchaUtils.generateCaptcha(6);
            assertTrue(Pattern.matches("\\d{6}", result), "验证码不是长度为6的数字:" + result);
        }
    }
}
