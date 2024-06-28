package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.GroupConnectService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupConnectServiceImpl implements GroupConnectService {

    @Autowired
    private GroupConnectMapper groupConnectMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addUserToGroup(Integer groupid, Integer userid) {
        if (!groupMapper.isGroupExist(groupid) || !userMapper.isUserExist(userid)) {
            return false;
        }
        if (groupConnectMapper.isMemberExist(groupid, userid)) {
            return false;
        }
        return groupConnectMapper.insertGroupMember(groupid, userid) == 1;
    }

    @Override
    public User getContactByUid(Integer groupid, Integer userid) {
        if (!groupMapper.isGroupExist(groupid) || !userMapper.isUserExist(userid)) {
            return null;
        }
        if (!groupConnectMapper.isMemberExist(groupid, userid)) {
            return null;
        }
        return userMapper.getUserByUid(userid);
    }

    @Override
    public List<User> getContactByName(Integer groupid, String username) {
        List<User> list = userMapper.getUserByUsername(username);
        List<User> userList = new ArrayList<>();
        for (User it : list) {
            if (groupConnectMapper.isMemberExist(groupid, it.getUid())) {
                userList.add(it);
            }
        }
        return userList;
    }

    @Override
    public List<Integer> getAllUidByGroupId(Integer groupid) {
        return groupConnectMapper.getAllUidBygroupid(groupid);
    }

    @Override
    public List<User> getAllUserByGroupId(Integer groupid) {
        List<Integer> idList = groupConnectMapper.getAllUidBygroupid(groupid);
        List<User> userList = new ArrayList<>();
        for (Integer it : idList) {
            userList.add(userMapper.getUserByUid(it));
        }
        return userList;
    }

    @Override
    public List<Group> getAllGroupOfUser(Integer uid) {
        List<Integer> list = groupConnectMapper.selectAllGroupOfUser(uid);
        List<Group> glist = new ArrayList<>();
        for (Integer it : list) {
            glist.add(groupMapper.selectById(it));
        }
        return glist;
    }

    @Override
    public boolean deleteMemberByUid(Integer groupid, Integer uid) {
        if (!groupConnectMapper.isMemberExist(groupid, uid)) {
            return false;
        }
        return groupConnectMapper.deleteContactById(groupid, uid) == 1;
    }

    //根据UID获取其所在群id
    @Override
    public List<Integer> getAllGroupidByUid(Integer uid){
        return groupConnectMapper.selectAllGroupOfUser(uid);
    }
}
