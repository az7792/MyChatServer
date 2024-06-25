package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Group;

import java.util.List;
import java.util.Map;

public interface GroupService {
    boolean isGroupExist(Integer groupid);
    boolean addGroupById(Group group);
    Group getGroupByUid(Integer groupid);
    List<Group> getGroupByName(String groupname);
    boolean updateGroupName(Integer groupid, String groupname);
    boolean deleteGroupByUid(Integer groupid);
}
