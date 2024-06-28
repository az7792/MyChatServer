package com.mychat.mychatserver.mapper;

import org.apache.ibatis.annotations.*;
import com.mychat.mychatserver.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    // 判断UID是否存在
    @Select("SELECT COUNT(*) > 0 FROM users WHERE uid = #{uid}")
    boolean isUserExist(Integer uid);

    // 判断邮箱是否存在
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean isEmailExist(String email);

    // 通过uid获取用户消息
    @Select("SELECT uid, username, email, avatar FROM users WHERE uid = #{uid}")
    User getUserByUid(Integer uid);

    // 通过邮箱获取用户消息
    @Select("SELECT uid, username, email, avatar FROM users WHERE email = #{email}")
    User getUserByEmail(String email);

    // 通过用户名获取用户消息
    @Select("SELECT uid, username, email FROM users WHERE username = #{username}")
    List<User> getUserByUsername(String username);

    // 增加用户，并获取自动生成的主键
    @Insert("INSERT INTO users (username, email, password, avatar) VALUES (#{username}, #{email}, #{password}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int addUser(User user);

    // 通过UID和密码进行登录匹配
    @Select("SELECT COUNT(*) > 0 FROM users WHERE uid = #{uid} AND password = #{password}")
    boolean matchByUidAndPassword(Integer uid, String password);

    // 通过邮箱和密码进行登录匹配
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email} AND password = #{password}")
    boolean matchByEmailAndPassword(String email, String password);

    // 修改用户的密码
    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    int updatePassword(String email, String password);

    // 根据 UID 获取用户头像
    @Select("SELECT avatar FROM users WHERE uid = #{uid}")
    String getAvatarByUid(Integer uid);

    // 根据 UID 更新用户头像
    @Update("UPDATE users SET avatar = #{avatar} WHERE uid = #{uid}")
    int updateAvatarByUid(@Param("uid") Integer uid, @Param("avatar") String avatar);

    //更新用户信息(不包含密码)
    @Update("UPDATE users SET username = #{username}, email = #{email}, avatar = #{avatar} WHERE uid = #{uid}")
    int updateUserInfo(User user);

}
