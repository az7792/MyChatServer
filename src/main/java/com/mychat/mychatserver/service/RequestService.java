package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Request;

public interface RequestService {
    boolean sendFriendRequest(Integer requesterId, Integer targetUserId, String message);
    boolean applyToJoinGroup(Integer groupId, Integer userId, String message);
}
