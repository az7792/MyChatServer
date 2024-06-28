package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.User;

public interface UserService {
    boolean isUserExist(Integer uid);
    boolean isEmailExist(String email);
    User getUserByUid(Integer uid);
    User getUserByEmail(String email);
    boolean register(User user);
    boolean loginByUID(Integer uid, String password);
    boolean loginByEmail(String email, String password);
    boolean updatePassword(String email, String password);
    String getAvatarByUid(Integer uid);
    boolean updateAvatarByUid(Integer uid, String avatar);
    boolean updateUserInfo(User user);
}
