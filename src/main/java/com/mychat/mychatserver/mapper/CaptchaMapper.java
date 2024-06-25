package com.mychat.mychatserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.mychat.mychatserver.entity.Captcha;

@Mapper
public interface CaptchaMapper {
    //插入一条验证码
    @Insert("""
            INSERT INTO captchas (email, code, created_at, is_used)
            VALUES (#{email}, #{code}, #{createdAt}, #{isUsed})
            ON DUPLICATE KEY UPDATE
            code = VALUES(code), created_at = VALUES(created_at), is_used = VALUES(is_used);""")
    int save(Captcha captcha);

    //匹配验证码(验证码未使用并且在1分钟有效期内)
    @Select("""
            SELECT COUNT(*) > 0 FROM captchas
            WHERE email = #{email} AND code = #{code} AND NOT is_used
            AND created_at > TIMESTAMPADD(MINUTE, -1, NOW());""")
    boolean matchCaptcha(String email, String code);

    //更新验证码状态
    @Update("UPDATE captchas SET is_used = #{isUsed} WHERE email = #{email}")
    void markCaptchaStatus(String email, boolean isUsed);

}
