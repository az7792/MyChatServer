package com.mychat.mychatserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSender EmailSender;
    //收件人邮箱地址、邮件主题和邮件内容
    public boolean sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("1460014874@qq.com");
        try {
            EmailSender.send(message);
            return true; // 发送成功
        } catch (MailException e) {
            e.printStackTrace();
            return false; // 发送失败
        }
    }
}
