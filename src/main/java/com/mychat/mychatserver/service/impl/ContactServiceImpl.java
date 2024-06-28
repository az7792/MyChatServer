package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Contact;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.ContactMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addContactById(Integer uid1, Integer uid2) {
        if(contactMapper.isContactExist(uid1, uid2)) {
            return false;
        }
        if((!userMapper.isUserExist(uid1))||(!userMapper.isUserExist(uid2))){
            return false;
        }
        contactMapper.addContactByUid(uid1, uid2);
        return true;
    }

    @Override
    public List<User> getContactByUid(Integer uid) {
        List<Contact> list = contactMapper.selectByUid(uid);
        List<User> userList = new ArrayList<>();
        for(Contact o : list){
            if(userMapper.isUserExist(o.getUid2())){
                userList.add(userMapper.getUserByUid(o.getUid2()));
            }
        }
        return userList;
    }

    @Override
    public boolean deleteContactByUid(Integer uid1, Integer uid2) {
        if(!contactMapper.isContactExist(uid1, uid2) || !contactMapper.isContactExist(uid2, uid1)) {
            return false;
        }
        return contactMapper.deleteContactById(uid1, uid2) == 1 && contactMapper.deleteContactById(uid2, uid1) == 1;
    }

    @Override
    public List<Integer> getIdsByUid(Integer uid) {
        return contactMapper.getIdsByUid(uid);
    }
}