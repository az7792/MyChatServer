package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Captcha;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional //测试完成后自动撤销修改，避免真正地写入数据到数据库中
public class CaptchaMapperTest {

    @Autowired
    private CaptchaMapper captchaMapper;

    @Test
    public void testCaptchaMapper() {
        Captcha captcha = new Captcha();
        captcha.setEmail("test@example.com");
        captcha.setCode("123456");
        captcha.setCreatedAt(LocalDateTime.now());
        captcha.setUsed(false);

        // 使用 captchaMapper 将 Captcha 对象插入到数据库中
        captchaMapper.save(captcha);

        // 匹配验证码
        boolean res = captchaMapper.matchCaptcha(captcha.getEmail(), captcha.getCode());
        assertTrue(res,"匹配失败");

        //测试更新验证码状态后再次进行匹配
        captchaMapper.markCaptchaStatus(captcha.getEmail(),true);
        // 匹配验证码
        res = captchaMapper.matchCaptcha(captcha.getEmail(), captcha.getCode());
        assertTrue(res==false,"应该匹配失败，但是匹配成功了");
    }
}
