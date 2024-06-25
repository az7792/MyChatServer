package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.mychat.mychatserver.entity.Captcha;
import com.mychat.mychatserver.mapper.CaptchaMapper;
import com.mychat.mychatserver.utils.CaptchaUtils;
import com.mychat.mychatserver.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "验证码管理")
@RestController
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @Operation(summary = "发送验证码")
    @PostMapping("/sendCaptchaByEmail")
    public Map<String, Object> sendCaptchaByEmail(String email) {
        Map<String, Object> response = new HashMap<>();
        boolean success = captchaService.sendCaptchaByEmail(email);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "匹配验证码")
    @PostMapping("/matchCaptcha")
    public Map<String, Object> matchCaptcha(String email, String code) {
        Map<String, Object> response = new HashMap<>();
        boolean success = captchaService.matchCaptcha(email, code);
        response.put("success", success);
        return response;
    }
}
