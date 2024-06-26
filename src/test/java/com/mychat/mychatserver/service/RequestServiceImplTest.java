package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Request;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.RequestMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RequestServiceImplTest {

    @Autowired
    private RequestServiceImpl requestService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private RequestMapper requestMapper;

    @MockBean
    private GroupMapper groupMapper;

    @MockBean
    private GroupConnectMapper groupConnectMapper;

    @Test
    public void testSendFriendRequest_Success() {
        User requester = new User();
        requester.setUid(1);
        when(userMapper.getUserByUid(1)).thenReturn(requester);

        User targetUser = new User();
        targetUser.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(targetUser);

        when(requestMapper.isFriend(1, 2)).thenReturn(false);
        when(requestMapper.insertRequest(any(Request.class))).thenReturn(1);

        boolean result = requestService.sendFriendRequest(1, 2, "Please add me as a friend");
        assertTrue(result, "Expected sendFriendRequest to succeed");
    }

    @Test
    public void testSendFriendRequest_RequesterNotFound() {
        when(userMapper.getUserByUid(1)).thenReturn(null);

        boolean result = requestService.sendFriendRequest(1, 2, "Please add me as a friend");
        assertFalse(result, "Expected sendFriendRequest to fail due to requester not found");
    }

    @Test
    public void testSendFriendRequest_TargetUserNotFound() {
        User requester = new User();
        requester.setUid(1);
        when(userMapper.getUserByUid(1)).thenReturn(requester);

        when(userMapper.getUserByUid(2)).thenReturn(null);

        boolean result = requestService.sendFriendRequest(1, 2, "Please add me as a friend");
        assertFalse(result, "Expected sendFriendRequest to fail due to target user not found");
    }

    @Test
    public void testSendFriendRequest_AlreadyFriends() {
        User requester = new User();
        requester.setUid(1);
        when(userMapper.getUserByUid(1)).thenReturn(requester);

        User targetUser = new User();
        targetUser.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(targetUser);

        when(requestMapper.isFriend(1, 2)).thenReturn(true);

        boolean result = requestService.sendFriendRequest(1, 2, "Please add me as a friend");
        assertFalse(result, "Expected sendFriendRequest to fail due to already friends");
    }

    @Test
    public void testApplyToJoinGroup_Success() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        User user = new User();
        user.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(user);
        when(groupMapper.getOwnerIdByGroupId(1)).thenReturn(3);
        when(requestMapper.insertRequest(any(Request.class))).thenReturn(1);

        boolean result = requestService.applyToJoinGroup(1, 2, "Please let me join the group");
        assertTrue(result, "Expected applyToJoinGroup to succeed");
    }

    @Test
    public void testApplyToJoinGroup_GroupNotExist() {
        when(groupMapper.isGroupExist(1)).thenReturn(false);

        boolean result = requestService.applyToJoinGroup(1, 2, "Please let me join the group");
        assertFalse(result, "Expected applyToJoinGroup to fail due to group not existing");
    }

    @Test
    public void testApplyToJoinGroup_AlreadyMember() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(true);

        boolean result = requestService.applyToJoinGroup(1, 2, "Please let me join the group");
        assertFalse(result, "Expected applyToJoinGroup to fail due to already a member");
    }

    @Test
    public void testApplyToJoinGroup_UserNotFound() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        when(userMapper.getUserByUid(2)).thenReturn(null);

        boolean result = requestService.applyToJoinGroup(1, 2, "Please let me join the group");
        assertFalse(result, "Expected applyToJoinGroup to fail due to user not found");
    }

    @Test
    public void testApplyToJoinGroup_GroupOwnerNotFound() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        User user = new User();
        user.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(user);
        when(groupMapper.getOwnerIdByGroupId(1)).thenReturn(null);

        boolean result = requestService.applyToJoinGroup(1, 2, "Please let me join the group");
        assertFalse(result, "Expected applyToJoinGroup to fail due to group owner not found");
    }
}
