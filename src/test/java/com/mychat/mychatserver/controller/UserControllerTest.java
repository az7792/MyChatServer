package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // 测试根据UID查询用户是否存在
    @Test
    public void testIsUserExist() throws Exception {
        Integer uid = 1;

        // 模拟用户存在
        when(userService.isUserExist(uid)).thenReturn(true);

        // 发起 POST 请求，查询用户是否存在
        mockMvc.perform(MockMvcRequestBuilders.post("/exists/uid")
                        .param("uid", uid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exist").value(true));
    }

    // 测试根据Email查询用户是否存在
    @Test
    public void testIsEmailExist() throws Exception {
        String email = "test@example.com";

        // 模拟用户邮箱存在
        when(userService.isEmailExist(email)).thenReturn(true);

        // 发起 POST 请求，查询邮箱是否存在
        mockMvc.perform(MockMvcRequestBuilders.post("/exists/email")
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exist").value(true));
    }

    // 测试根据UID获取用户信息
    @Test
    public void testGetUserByUid() throws Exception {
        Integer uid = 1;
        User user = new User();
        user.setUid(uid);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        // 模拟根据UID获取用户信息
        when(userService.getUserByUid(uid)).thenReturn(user);

        // 发起 POST 请求，根据UID获取用户信息
        mockMvc.perform(MockMvcRequestBuilders.post("/getUser/uid")
                        .param("uid", uid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").value(uid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testuser@example.com"));
    }

    // 测试根据Email获取用户信息
    @Test
    public void testGetUserByEmail() throws Exception {
        String email = "testuser@example.com";
        User user = new User();
        user.setEmail(email);
        user.setUid(1);
        user.setUsername("testuser");

        // 模拟根据Email获取用户信息
        when(userService.getUserByEmail(email)).thenReturn(user);

        // 发起 POST 请求，根据Email获取用户信息
        mockMvc.perform(MockMvcRequestBuilders.post("/getUser/email")
                        .param("email", email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email));
    }

    // 测试注册新用户成功
    @Test
    public void testRegisterSuccess() throws Exception {
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        // 模拟注册成功
        when(userService.register(any(User.class))).thenReturn(true);

        // 发起 POST 请求，注册新用户
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试注册新用户失败
    @Test
    public void testRegisterFailure() throws Exception {
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        // 模拟注册失败
        when(userService.register(any(User.class))).thenReturn(false);

        // 发起 POST 请求，注册新用户
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试通过UID登录成功
    @Test
    public void testLoginByUIDSuccess() throws Exception {
        Integer uid = 1;
        String password = "password";

        // 模拟登录成功
        when(userService.loginByUID(uid, password)).thenReturn(true);

        // 发起 POST 请求，通过UID登录
        mockMvc.perform(MockMvcRequestBuilders.post("/login/uid")
                        .param("uid", uid.toString())
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试通过UID登录失败
    @Test
    public void testLoginByUIDFailure() throws Exception {
        Integer uid = 1;
        String password = "password";

        // 模拟登录失败
        when(userService.loginByUID(uid, password)).thenReturn(false);

        // 发起 POST 请求，通过UID登录
        mockMvc.perform(MockMvcRequestBuilders.post("/login/uid")
                        .param("uid", uid.toString())
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试通过Email登录成功
    @Test
    public void testLoginByEmailSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password";

        // 模拟登录成功
        when(userService.loginByEmail(email, password)).thenReturn(true);

        // 发起 POST 请求，通过Email登录
        mockMvc.perform(MockMvcRequestBuilders.post("/login/email")
                        .param("email", email)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试通过Email登录失败
    @Test
    public void testLoginByEmailFailure() throws Exception {
        String email = "test@example.com";
        String password = "password";

        // 模拟登录失败
        when(userService.loginByEmail(email, password)).thenReturn(false);

        // 发起 POST 请求，通过Email登录
        mockMvc.perform(MockMvcRequestBuilders.post("/login/email")
                        .param("email", email)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试通过Email修改密码成功
    @Test
    public void testUpdatePasswordSuccess() throws Exception {
        String email = "test@example.com";
        String newPassword = "newpassword";

        // 模拟修改密码成功
        when(userService.updatePassword(email, newPassword)).thenReturn(true);

        // 发起 POST 请求，修改密码
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePassword")
                        .param("email", email)
                        .param("password", newPassword))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试通过Email修改密码失败
    @Test
    public void testUpdatePasswordFailure() throws Exception {
        String email = "test@example.com";
        String newPassword = "newpassword";

        // 模拟修改密码失败
        when(userService.updatePassword(email, newPassword)).thenReturn(false);

        // 发起 POST 请求，修改密码
        mockMvc.perform(MockMvcRequestBuilders.post("/updatePassword")
                        .param("email", email)
                        .param("password", newPassword))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试根据UID获取头像
    @Test
    public void testGetAvatarByUid() throws Exception {
        Integer uid = 1;
        String avatar = "avatar_url";

        // 模拟获取头像成功
        when(userService.getAvatarByUid(uid)).thenReturn(avatar);

        // 发起 POST 请求，根据UID获取头像
        mockMvc.perform(MockMvcRequestBuilders.post("/getAvatar")
                        .param("uid", uid.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avatar").value(avatar));
    }

    // 测试根据UID更新头像成功
    @Test
    public void testUpdateAvatarByUidSuccess() throws Exception {
        Integer uid = 1;
        String newAvatar = "new_avatar_url";

        // 模拟更新头像成功
        when(userService.updateAvatarByUid(uid, newAvatar)).thenReturn(true);

        // 发起 POST 请求，根据UID更新头像
        mockMvc.perform(MockMvcRequestBuilders.post("/updateAvatar")
                        .param("uid", uid.toString())
                        .param("avatar", newAvatar))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试根据UID更新头像失败
    @Test
    public void testUpdateAvatarByUidFailure() throws Exception {
        Integer uid = 1;
        String newAvatar = "new_avatar_url";

        // 模拟更新头像失败
        when(userService.updateAvatarByUid(uid, newAvatar)).thenReturn(false);

        // 发起 POST 请求，根据UID更新头像
        mockMvc.perform(MockMvcRequestBuilders.post("/updateAvatar")
                        .param("uid", uid.toString())
                        .param("avatar", newAvatar))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }
}
