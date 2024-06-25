package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Captcha;
import com.mychat.mychatserver.mapper.CaptchaMapper;
import com.mychat.mychatserver.service.CaptchaService;
import com.mychat.mychatserver.utils.CaptchaUtils;
import com.mychat.mychatserver.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private CaptchaMapper captchaMapper;

    @Override
    //通过邮箱发送随机验证
    public boolean sendCaptchaByEmail(String email) {
        String captcha = CaptchaUtils.generateCaptcha(6); // 生成6位数字验证码
        boolean success = emailUtils.sendEmail(email, "验证码", "您的验证码是："+captcha);
        if (success) {
            // 保存验证码到数据库
            Captcha captchaEntity = new Captcha();
            captchaEntity.setEmail(email);
            captchaEntity.setCode(captcha);
            captchaEntity.setCreatedAt(LocalDateTime.now());
            captchaEntity.setUsed(false);
            captchaMapper.save(captchaEntity);
        }
        return success;
    }

    @Override
    //匹配验证码
    public boolean matchCaptcha(String email, String code) {
        boolean success = captchaMapper.matchCaptcha(email, code);
        if (success) {//匹配成功则设置验证码为已使用
            captchaMapper.markCaptchaStatus(email,true);
        }
        return success;
    }
}
