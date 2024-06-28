package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isUserExist(Integer uid) {
        return userMapper.isUserExist(uid);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userMapper.isEmailExist(email);
    }

    @Override
    public User getUserByUid(Integer uid) {
        return userMapper.getUserByUid(uid);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public boolean register(User user) {
        if (userMapper.isEmailExist(user.getEmail())) {
            return false;
        }
        return userMapper.addUser(user) == 1;
    }

    @Override
    public boolean loginByUID(Integer uid, String password) {
        return userMapper.matchByUidAndPassword(uid, password);
    }

    @Override
    public boolean loginByEmail(String email, String password) {
        return userMapper.matchByEmailAndPassword(email, password);
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return userMapper.updatePassword(email, password) == 1;
    }

    @Override
    public String getAvatarByUid(Integer uid) {
        return userMapper.getAvatarByUid(uid);
    }

    @Override
    public boolean updateAvatarByUid(Integer uid, String avatar) {
        return userMapper.updateAvatarByUid(uid, avatar) == 1;
    }

    @Override
    public boolean updateUserInfo(User user){
        if (!userMapper.isUserExist(user.getUid())) {
            return false;
        }
        return userMapper.updateUserInfo(user)>=0;
    }
}
