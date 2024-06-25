package com.mychat.mychatserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsUserExist() {
        Integer uid = 1;
        when(userMapper.isUserExist(uid)).thenReturn(true);

        boolean result = userService.isUserExist(uid);

        assertTrue(result);
        verify(userMapper, times(1)).isUserExist(uid);
    }

    @Test
    public void testIsEmailExist() {
        String email = "test@example.com";
        when(userMapper.isEmailExist(email)).thenReturn(true);

        boolean result = userService.isEmailExist(email);

        assertTrue(result);
        verify(userMapper, times(1)).isEmailExist(email);
    }

    @Test
    public void testGetUserByUid() {
        Integer uid = 1;
        User user = new User();
        user.setUid(uid);
        when(userMapper.getUserByUid(uid)).thenReturn(user);

        User result = userService.getUserByUid(uid);

        assertNotNull(result);
        assertEquals(uid, result.getUid());
        verify(userMapper, times(1)).getUserByUid(uid);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        when(userMapper.getUserByEmail(email)).thenReturn(user);

        User result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userMapper, times(1)).getUserByEmail(email);
    }

    @Test
    public void testRegisterSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userMapper.isEmailExist(user.getEmail())).thenReturn(false);
        when(userMapper.addUser(user)).thenReturn(1);

        boolean result = userService.register(user);

        assertTrue(result);
        verify(userMapper, times(1)).isEmailExist(user.getEmail());
        verify(userMapper, times(1)).addUser(user);
    }

    @Test
    public void testRegisterFailure() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userMapper.isEmailExist(user.getEmail())).thenReturn(true);

        boolean result = userService.register(user);

        assertFalse(result);
        verify(userMapper, times(1)).isEmailExist(user.getEmail());
        verify(userMapper, never()).addUser(user);
    }

    @Test
    public void testLoginByUIDSuccess() {
        Integer uid = 1;
        String password = "password";
        when(userMapper.matchByUidAndPassword(uid, password)).thenReturn(true);

        boolean result = userService.loginByUID(uid, password);

        assertTrue(result);
        verify(userMapper, times(1)).matchByUidAndPassword(uid, password);
    }

    @Test
    public void testLoginByUIDFailure() {
        Integer uid = 1;
        String password = "password";
        when(userMapper.matchByUidAndPassword(uid, password)).thenReturn(false);

        boolean result = userService.loginByUID(uid, password);

        assertFalse(result);
        verify(userMapper, times(1)).matchByUidAndPassword(uid, password);
    }

    @Test
    public void testLoginByEmailSuccess() {
        String email = "test@example.com";
        String password = "password";
        when(userMapper.matchByEmailAndPassword(email, password)).thenReturn(true);

        boolean result = userService.loginByEmail(email, password);

        assertTrue(result);
        verify(userMapper, times(1)).matchByEmailAndPassword(email, password);
    }

    @Test
    public void testLoginByEmailFailure() {
        String email = "test@example.com";
        String password = "password";
        when(userMapper.matchByEmailAndPassword(email, password)).thenReturn(false);

        boolean result = userService.loginByEmail(email, password);

        assertFalse(result);
        verify(userMapper, times(1)).matchByEmailAndPassword(email, password);
    }

    @Test
    public void testUpdatePasswordSuccess() {
        String email = "test@example.com";
        String newPassword = "newpassword";
        when(userMapper.updatePassword(email, newPassword)).thenReturn(1);

        boolean result = userService.updatePassword(email, newPassword);

        assertTrue(result);
        verify(userMapper, times(1)).updatePassword(email, newPassword);
    }

    @Test
    public void testUpdatePasswordFailure() {
        String email = "test@example.com";
        String newPassword = "newpassword";
        when(userMapper.updatePassword(email, newPassword)).thenReturn(0);

        boolean result = userService.updatePassword(email, newPassword);

        assertFalse(result);
        verify(userMapper, times(1)).updatePassword(email, newPassword);
    }

    @Test
    public void testGetAvatarByUid() {
        Integer uid = 1;
        String avatar = "avatar_url";
        when(userMapper.getAvatarByUid(uid)).thenReturn(avatar);

        String result = userService.getAvatarByUid(uid);

        assertEquals(avatar, result);
        verify(userMapper, times(1)).getAvatarByUid(uid);
    }

    @Test
    public void testUpdateAvatarByUidSuccess() {
        Integer uid = 1;
        String newAvatar = "new_avatar_url";
        when(userMapper.updateAvatarByUid(uid, newAvatar)).thenReturn(1);

        boolean result = userService.updateAvatarByUid(uid, newAvatar);

        assertTrue(result);
        verify(userMapper, times(1)).updateAvatarByUid(uid, newAvatar);
    }

    @Test
    public void testUpdateAvatarByUidFailure() {
        Integer uid = 1;
        String newAvatar = "new_avatar_url";
        when(userMapper.updateAvatarByUid(uid, newAvatar)).thenReturn(0);

        boolean result = userService.updateAvatarByUid(uid, newAvatar);

        assertFalse(result);
        verify(userMapper, times(1)).updateAvatarByUid(uid, newAvatar);
    }
}
