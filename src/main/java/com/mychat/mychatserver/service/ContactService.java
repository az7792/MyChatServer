package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.User;

import java.util.List;
import java.util.Map;

public interface ContactService {
    boolean addContactById(Integer uid1, Integer uid2);
    List<User> getContactByUid(Integer uid);
    boolean deleteContactByUid(Integer uid1, Integer uid2);
}