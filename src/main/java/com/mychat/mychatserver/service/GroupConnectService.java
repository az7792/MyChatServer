package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;

import java.util.List;

public interface GroupConnectService {
    boolean addUserToGroup(Integer groupid, Integer userid);
    User getContactByUid(Integer groupid, Integer userid);
    List<User> getContactByName(Integer groupid, String username);
    List<Integer> getAllUidByGroupId(Integer groupid);
    List<User> getAllUserByGroupId(Integer groupid);
    List<Group> getAllGroupOfUser(Integer uid);
    boolean deleteMemberByUid(Integer groupid, Integer uid);
}
