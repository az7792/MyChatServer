package com.mychat.mychatserver.service;

public interface CaptchaService {
    boolean sendCaptchaByEmail(String email);
    boolean matchCaptcha(String email, String code);
}
