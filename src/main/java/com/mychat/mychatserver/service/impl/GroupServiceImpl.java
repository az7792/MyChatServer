package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupConnectMapper groupConnectMapper;

    @Override
    public boolean isGroupExist(Integer groupid) {
        return groupMapper.isGroupExist(groupid);
    }

    @Override
    public boolean addGroupById(Group group) {
        if (!userMapper.isUserExist(group.getOwnerid())) {
            return false;
        }
        int status1 = groupMapper.insert(group);
        int status2 = groupConnectMapper.insertGroupMember(group.getGroupid(), group.getOwnerid());
        return status1 > 0 && status2 > 0;
    }

    @Override
    public Group getGroupByUid(Integer groupid) {
        if (!groupMapper.isGroupExist(groupid)) {
            return null;
        }
        return groupMapper.selectById(groupid);
    }

    @Override
    public List<Group> getGroupByName(String groupname) {
        return groupMapper.selectByName(groupname);
    }

    @Override
    public boolean updateGroupName(Integer groupid, String groupname) {
        if (!groupMapper.isGroupExist(groupid)) {
            return false;
        }
        return groupMapper.updateGroupName(groupid, groupname) > 0;
    }

    @Override
    public boolean deleteGroupByUid(Integer groupid) {
        if (!groupMapper.isGroupExist(groupid)) {
            return false;
        }
        return groupMapper.deleteByGroupId(groupid) > 0;
    }
}
