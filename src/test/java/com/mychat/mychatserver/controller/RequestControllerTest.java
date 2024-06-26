package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.RequestMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private RequestMapper requestMapper;

    @MockBean
    private GroupMapper groupMapper;

    @MockBean
    private GroupConnectMapper groupConnectMapper;

    @Test
    public void testSendFriendRequest_Success() throws Exception {
        // Mock the UserMapper behavior
        User requester = new User();
        requester.setUid(1);
        requester.setUsername("requester");
        when(userMapper.getUserByUid(1)).thenReturn(requester);

        User targetUser = new User();
        targetUser.setUid(2);
        targetUser.setUsername("targetUser");
        when(userMapper.getUserByUid(2)).thenReturn(targetUser);

        when(requestMapper.isFriend(1, 2)).thenReturn(false);
        when(requestMapper.insertRequest(any())).thenReturn(1);

        // Perform the POST request
        mockMvc.perform(post("/request/friend")
                        .param("requesterId", "1")
                        .param("targetUserId", "2")
                        .param("message", "Please add me as a friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("好友请求已提交"));
    }

    @Test
    public void testSendFriendRequest_UserNotFound() throws Exception {
        when(userMapper.getUserByUid(1)).thenReturn(null);

        // Perform the POST request
        mockMvc.perform(post("/request/friend")
                        .param("requesterId", "1")
                        .param("targetUserId", "2")
                        .param("message", "Please add me as a friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("请求者用户不存在"));
    }

    @Test
    public void testSendFriendRequest_AlreadyFriends() throws Exception {
        User requester = new User();
        requester.setUid(1);
        when(userMapper.getUserByUid(1)).thenReturn(requester);

        User targetUser = new User();
        targetUser.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(targetUser);

        when(requestMapper.isFriend(1, 2)).thenReturn(true);

        // Perform the POST request
        mockMvc.perform(post("/request/friend")
                        .param("requesterId", "1")
                        .param("targetUserId", "2")
                        .param("message", "Please add me as a friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("你们已经是好友"));
    }

    @Test
    public void testApplyToJoinGroup_Success() throws Exception {
        // Mock the behavior of mappers
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        User user = new User();
        user.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(user);
        when(groupMapper.getOwnerIdByGroupId(1)).thenReturn(3);
        when(requestMapper.insertRequest(any())).thenReturn(1);

        // Perform the POST request
        mockMvc.perform(post("/request/group")
                        .param("groupId", "1")
                        .param("userId", "2")
                        .param("message", "Please let me join the group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("群组申请已提交"));
    }

    @Test
    public void testApplyToJoinGroup_GroupNotExist() throws Exception {
        when(groupMapper.isGroupExist(1)).thenReturn(false);

        // Perform the POST request
        mockMvc.perform(post("/request/group")
                        .param("groupId", "1")
                        .param("userId", "2")
                        .param("message", "Please let me join the group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("群组不存在"));
    }

    @Test
    public void testApplyToJoinGroup_UserAlreadyInGroup() throws Exception {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(true);

        // Perform the POST request
        mockMvc.perform(post("/request/group")
                        .param("groupId", "1")
                        .param("userId", "2")
                        .param("message", "Please let me join the group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("用户已经在群组中"));
    }

    @Test
    public void testApplyToJoinGroup_UserNotFound() throws Exception {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        when(userMapper.getUserByUid(2)).thenReturn(null);

        // Perform the POST request
        mockMvc.perform(post("/request/group")
                        .param("groupId", "1")
                        .param("userId", "2")
                        .param("message", "Please let me join the group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("用户不存在"));
    }

    @Test
    public void testApplyToJoinGroup_GroupOwnerNotFound() throws Exception {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupConnectMapper.isMemberExist(1, 2)).thenReturn(false);
        User user = new User();
        user.setUid(2);
        when(userMapper.getUserByUid(2)).thenReturn(user);
        when(groupMapper.getOwnerIdByGroupId(1)).thenReturn(null);

        // Perform the POST request
        mockMvc.perform(post("/request/group")
                        .param("groupId", "1")
                        .param("userId", "2")
                        .param("message", "Please let me join the group"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("无法找到群主"));
    }
}
