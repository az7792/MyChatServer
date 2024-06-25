package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 测试完成后自动撤销修改，避免真正地写入数据到数据库中
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setAvatar("avatar_url");

        // 增加用户，并获取自动生成的主键
        int insertResult = userMapper.addUser(user);
        assertEquals(1, insertResult, "用户插入失败");

        // 验证 UID 已经生成并设置
        assertNotNull(user.getUid(), "用户 UID 未生成");

        // 判断 UID 是否存在
        boolean uidExist = userMapper.isUserExist(user.getUid());
        assertTrue(uidExist, "用户 UID 不存在");

        // 判断邮箱是否存在
        boolean emailExist = userMapper.isEmailExist(user.getEmail());
        assertTrue(emailExist, "用户邮箱不存在");

        // 通过 UID 获取用户信息
        User fetchedUser = userMapper.getUserByUid(user.getUid());
        assertNotNull(fetchedUser, "通过 UID 获取用户信息失败");
        assertEquals(user.getEmail(), fetchedUser.getEmail(), "获取的用户邮箱不匹配");

        // 通过邮箱获取用户信息
        fetchedUser = userMapper.getUserByEmail(user.getEmail());
        assertNotNull(fetchedUser, "通过邮箱获取用户信息失败");
        assertEquals(user.getUid(), fetchedUser.getUid(), "获取的用户 UID 不匹配");

        // 通过用户名获取用户信息
        List<User> usersByUsername = userMapper.getUserByUsername(user.getUsername());
        assertFalse(usersByUsername.isEmpty(), "通过用户名获取用户信息失败");
        assertEquals(user.getEmail(), usersByUsername.get(0).getEmail(), "获取的用户邮箱不匹配");

        // 通过 UID 和密码进行登录匹配
        boolean loginMatch = userMapper.matchByUidAndPassword(user.getUid(), user.getPassword());
        assertTrue(loginMatch, "通过 UID 和密码进行登录匹配失败");

        // 通过邮箱和密码进行登录匹配
        loginMatch = userMapper.matchByEmailAndPassword(user.getEmail(), user.getPassword());
        assertTrue(loginMatch, "通过邮箱和密码进行登录匹配失败");

        // 修改用户的密码
        String newPassword = "newpassword";
        int updateResult = userMapper.updatePassword(user.getEmail(), newPassword);
        assertEquals(1, updateResult, "修改用户密码失败");

        // 更新密码后再次进行登录匹配
        loginMatch = userMapper.matchByEmailAndPassword(user.getEmail(), newPassword);
        assertTrue(loginMatch, "更新密码后通过邮箱和密码进行登录匹配失败");

        // 获取用户头像
        String avatar = userMapper.getAvatarByUid(user.getUid());
        assertEquals(user.getAvatar(), avatar, "获取用户头像失败");

        // 更新用户头像
        String newAvatar = "new_avatar_url";
        updateResult = userMapper.updateAvatarByUid(user.getUid(), newAvatar);
        assertEquals(1, updateResult, "更新用户头像失败");

        // 获取更新后的用户头像
        avatar = userMapper.getAvatarByUid(user.getUid());
        assertEquals(newAvatar, avatar, "获取更新后的用户头像失败");
    }
}
