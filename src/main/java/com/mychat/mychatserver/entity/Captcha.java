package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
@TableName("captchas")
public class Captcha {
    private String email; // 验证码发起者邮箱
    private String code;  //验证码
    private LocalDateTime createdAt; //验证码创建时间
    private boolean isUsed = false; //是否已使用

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
