package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Request;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.RequestMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupConnectMapper groupConnectMapper;

    @Override
    public boolean sendFriendRequest(Integer requesterId, Integer targetUserId, String message) {
        // 检查请求者是否存在
        User requester = userMapper.getUserByUid(requesterId);
        if (requester == null) {
            return false;
        }

        // 检查目标用户是否存在
        User targetUser = userMapper.getUserByUid(targetUserId);
        if (targetUser == null) {
            return false;
        }

        // 检查是否已经是好友
        boolean isFriend = requestMapper.isFriend(requesterId, targetUserId);
        if (isFriend) {
            return false;
        }

        // 创建新的好友请求
        Request request = new Request();
        request.setRequesterId(requesterId);
        request.setTargetUserId(targetUserId);
        request.setMessage(message);
        request.setCreatedAt(LocalDateTime.now());
        request.setRequestType("FRIEND");
        request.setApproved(false);

        return requestMapper.insertRequest(request) > 0;
    }

    @Override
    public boolean applyToJoinGroup(Integer groupId, Integer userId, String message) {
        // 检查群组是否存在
        boolean groupExists = groupMapper.isGroupExist(groupId);
        if (!groupExists) {
            return false;
        }

        // 检查用户是否已经在群组中
        boolean isMember = groupConnectMapper.isMemberExist(groupId, userId);
        if (isMember) {
            return false;
        }

        // 检查申请用户是否存在
        User user = userMapper.getUserByUid(userId);
        if (user == null) {
            return false;
        }

        // 查找群主ID
        Integer ownerId = groupMapper.getOwnerIdByGroupId(groupId);
        if (ownerId == null) {
            return false;
        }

        // 创建新的群组申请
        Request request = new Request();
        request.setRequesterId(userId);
        request.setTargetGroupId(groupId); // 对于群组请求，使用 targetGroupId
        request.setMessage(message);
        request.setTargetUserId(ownerId);
        request.setCreatedAt(LocalDateTime.now());
        request.setRequestType("GROUP");
        request.setApproved(false);

        return requestMapper.insertRequest(request) > 0;
    }
}
