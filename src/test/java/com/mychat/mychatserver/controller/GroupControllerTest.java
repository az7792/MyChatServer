package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    // 测试根据ID查询群组是否存在
    @Test
    public void testIsGroupExist() throws Exception {
        Integer groupId = 1;

        // 模拟群组存在
        when(groupService.isGroupExist(groupId)).thenReturn(true);

        // 发起 GET 请求，查询群组是否存在
        mockMvc.perform(MockMvcRequestBuilders.get("/exists/groupid")
                        .param("groupid", groupId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exist").value(true));
    }

    // 测试创建群组成功
    @Test
    public void testCreateGroupSuccess() throws Exception {
        Group group = new Group();
        group.setGroupid(1);
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        // 模拟创建群组成功
        when(groupService.addGroupById(any(Group.class))).thenReturn(true);

        // 发起 POST 请求，创建群组
        mockMvc.perform(MockMvcRequestBuilders.post("/creatgroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupname\": \"Test Group\", \"ownerid\": 1}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试创建群组失败
    @Test
    public void testCreateGroupFailure() throws Exception {
        Group group = new Group();
        group.setGroupid(1);
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        // 模拟创建群组失败
        when(groupService.addGroupById(any(Group.class))).thenReturn(false);

        // 发起 POST 请求，创建群组
        mockMvc.perform(MockMvcRequestBuilders.post("/creatgroup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupname\": \"Test Group\", \"ownerid\": 1}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试根据群ID查询群组
    @Test
    public void testGetGroupById() throws Exception {
        Integer groupId = 1;
        Group group = new Group();
        group.setGroupid(groupId);
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        // 模拟根据群ID获取群组信息
        when(groupService.getGroupByUid(groupId)).thenReturn(group);

        // 发起 POST 请求，根据群ID获取群组信息
        mockMvc.perform(MockMvcRequestBuilders.post("/selectgroup/uid")
                        .param("groupid", groupId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupid").value(groupId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.groupname").value("Test Group"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerid").value(1));
    }

    // 测试根据群名称查询群组
    @Test
    public void testGetGroupByName() throws Exception {
        String groupName = "Test Group";
        Group group = new Group();
        group.setGroupid(1);
        group.setGroupname(groupName);
        group.setOwnerid(1);

        List<Group> groupList = Arrays.asList(group);

        // 模拟根据群名称获取群组信息
        when(groupService.getGroupByName(groupName)).thenReturn(groupList);

        // 发起 GET 请求，根据群名称获取群组信息
        mockMvc.perform(MockMvcRequestBuilders.get("/selectgroup/name")
                        .param("groupname", groupName))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].groupid").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].groupname").value(groupName))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ownerid").value(1));
    }

    // 测试修改群名称成功
    @Test
    public void testUpdateGroupNameSuccess() throws Exception {
        Integer groupId = 1;
        String newGroupName = "New Test Group";

        // 模拟修改群名称成功
        when(groupService.updateGroupName(groupId, newGroupName)).thenReturn(true);

        // 发起 PUT 请求，修改群名称
        mockMvc.perform(MockMvcRequestBuilders.put("/updategroup")
                        .param("groupid", groupId.toString())
                        .param("groupname", newGroupName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试修改群名称失败
    @Test
    public void testUpdateGroupNameFailure() throws Exception {
        Integer groupId = 1;
        String newGroupName = "New Test Group";

        // 模拟修改群名称失败
        when(groupService.updateGroupName(groupId, newGroupName)).thenReturn(false);

        // 发起 PUT 请求，修改群名称
        mockMvc.perform(MockMvcRequestBuilders.put("/updategroup")
                        .param("groupid", groupId.toString())
                        .param("groupname", newGroupName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }

    // 测试根据群组ID删除群组成功
    @Test
    public void testDeleteGroupByIdSuccess() throws Exception {
        Integer groupId = 1;

        // 模拟删除群组成功
        when(groupService.deleteGroupByUid(groupId)).thenReturn(true);

        // 发起 DELETE 请求，删除群组
        mockMvc.perform(MockMvcRequestBuilders.delete("/deletegroup/uid")
                        .param("groupid", groupId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    // 测试根据群组ID删除群组失败
    @Test
    public void testDeleteGroupByIdFailure() throws Exception {
        Integer groupId = 1;

        // 模拟删除群组失败
        when(groupService.deleteGroupByUid(groupId)).thenReturn(false);

        // 发起 DELETE 请求，删除群组
        mockMvc.perform(MockMvcRequestBuilders.delete("/deletegroup/uid")
                        .param("groupid", groupId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));
    }
}
