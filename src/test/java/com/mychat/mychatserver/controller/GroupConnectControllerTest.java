package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.GroupConnectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupConnectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupConnectService groupConnectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUserToGroup() throws Exception {
        when(groupConnectService.addUserToGroup(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(post("/addusertogroup")
                        .param("groupid", "1")
                        .param("userid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testGetContactByUid() throws Exception {
        User user = new User();
        user.setUid(1);
        user.setUsername("testuser");

        when(groupConnectService.getContactByUid(anyInt(), anyInt())).thenReturn(user);

        mockMvc.perform(post("/selectuseringroup/uid")
                        .param("groupid", "1")
                        .param("userid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testGetContactByName() throws Exception {
        User user1 = new User();
        user1.setUid(1);
        user1.setUsername("testuser1");

        User user2 = new User();
        user2.setUid(2);
        user2.setUsername("testuser2");

        List<User> userList = Arrays.asList(user1, user2);

        when(groupConnectService.getContactByName(anyInt(), anyString())).thenReturn(userList);

        mockMvc.perform(post("/selectuseringroup/username")
                        .param("groupid", "1")
                        .param("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uid").value(1))
                .andExpect(jsonPath("$[0].username").value("testuser1"))
                .andExpect(jsonPath("$[1].uid").value(2))
                .andExpect(jsonPath("$[1].username").value("testuser2"));
    }

    @Test
    public void testGetAllUidByGroupId() throws Exception {
        List<Integer> uidList = Arrays.asList(1, 2, 3);

        when(groupConnectService.getAllUidByGroupId(anyInt())).thenReturn(uidList);

        mockMvc.perform(post("/getgroupmembers/uid")
                        .param("groupid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2))
                .andExpect(jsonPath("$[2]").value(3));
    }

    @Test
    public void testGetAllUserByGroupId() throws Exception {
        User user1 = new User();
        user1.setUid(1);
        user1.setUsername("testuser1");

        User user2 = new User();
        user2.setUid(2);
        user2.setUsername("testuser2");

        List<User> userList = Arrays.asList(user1, user2);

        when(groupConnectService.getAllUserByGroupId(anyInt())).thenReturn(userList);

        mockMvc.perform(post("/getgroupmembers/user")
                        .param("groupid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].uid").value(1))
                .andExpect(jsonPath("$[0].username").value("testuser1"))
                .andExpect(jsonPath("$[1].uid").value(2))
                .andExpect(jsonPath("$[1].username").value("testuser2"));
    }

    @Test
    public void testGetAllGroupOfUser() throws Exception {
        Group group1 = new Group();
        group1.setGroupid(1); // Ensure this matches your entity field name
        group1.setGroupname("Group1");

        Group group2 = new Group();
        group2.setGroupid(2); // Ensure this matches your entity field name
        group2.setGroupname("Group2");

        List<Group> groupList = Arrays.asList(group1, group2);

        when(groupConnectService.getAllGroupOfUser(anyInt())).thenReturn(groupList);

        mockMvc.perform(post("/getgroups/uid")
                        .param("uid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].groupid").value(1)) // Corrected to match your actual field name
                .andExpect(jsonPath("$[0].groupname").value("Group1"))
                .andExpect(jsonPath("$[1].groupid").value(2)) // Corrected to match your actual field name
                .andExpect(jsonPath("$[1].groupname").value("Group2"));
    }

    @Test
    public void testDeleteMemberByUid() throws Exception {
        when(groupConnectService.deleteMemberByUid(anyInt(), anyInt())).thenReturn(true);

        mockMvc.perform(post("/deletemember/uid")
                        .param("groupid", "1")
                        .param("uid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
