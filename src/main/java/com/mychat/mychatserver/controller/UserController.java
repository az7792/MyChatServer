package com.mychat.mychatserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "根据id查询用户是否存在")
    @PostMapping("/exists/uid")
    public Map<String, Object> isUserExist(Integer uid) {
        Map<String, Object> response = new HashMap<>();
        response.put("exist", userService.isUserExist(uid));
        return response;
    }

    @Operation(summary = "根据email查询用户是否存在")
    @PostMapping("/exists/email")
    public Map<String, Object> isEmailExist(String email) {
        Map<String, Object> response = new HashMap<>();
        response.put("exist", userService.isEmailExist(email));
        return response;
    }

    @Operation(summary = "根据UID查询用户信息")
    @PostMapping("/getUser/uid")
    public User getUserByUid(Integer uid) {
        return userService.getUserByUid(uid);
    }

    @Operation(summary = "根据邮箱查询用户信息")
    @PostMapping("/getUser/email")
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Operation(summary = "注册新用户")
    @PostMapping("/register")
    public Map<String, Object> register(User user) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.register(user);
        response.put("success", success);
        return response;
    }
    @Operation(summary = "更新用户信息(用户名头像邮箱)")
    @PostMapping("/updateUserInfo")
    public Map<String, Object> updateUserInfo(User user) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.updateUserInfo(user);
        response.put("success", success);
        return response;
    }


    @Operation(summary = "通过UID登录")
    @PostMapping("/login/uid")
    public Map<String, Object> loginByUID(Integer uid, String password) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.loginByUID(uid, password);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "通过邮箱登录")
    @PostMapping("/login/email")
    public Map<String, Object> loginByEmail(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.loginByEmail(email, password);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "通过邮箱修改密码")
    @PostMapping("/updatePassword")
    public Map<String, Object> updatePassword(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.updatePassword(email, password);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据UID获取头像")
    @PostMapping("/getAvatar")
    public Map<String, Object> getAvatarByUid(Integer uid) {
        Map<String, Object> response = new HashMap<>();
        String avatar = userService.getAvatarByUid(uid);
        if (avatar == null) {
            response.put("success", false);
            response.put("message", "Avatar not found");
        } else {
            response.put("success", true);
            response.put("avatar", avatar);
        }
        return response;
    }

    @Operation(summary = "根据UID更新头像")
    @PostMapping("/updateAvatar")
    public Map<String, Object> updateAvatarByUid(Integer uid, String avatar) {
        Map<String, Object> response = new HashMap<>();
        boolean success = userService.updateAvatarByUid(uid, avatar);
        response.put("success", success);
        return response;
    }
}
