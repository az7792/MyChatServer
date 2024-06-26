package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void testAddContactById_Success() throws Exception {
        // 模拟 contactService 的行为
        when(contactService.addContactById(anyInt(), anyInt())).thenReturn(true);

        // 发起 POST 请求，添加联系人
        mockMvc.perform(post("/contact/add")
                        .param("uid1", "1")
                        .param("uid2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testAddContactById_Failure() throws Exception {
        // 模拟 contactService 的行为
        when(contactService.addContactById(anyInt(), anyInt())).thenReturn(false);

        // 发起 POST 请求，添加联系人
        mockMvc.perform(post("/contact/add")
                        .param("uid1", "1")
                        .param("uid2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void testGetContactByUid_Success() throws Exception {
        // 模拟 contactService 的行为
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUid(2);
        user.setUsername("testuser");
        userList.add(user);
        when(contactService.getContactByUid(anyInt())).thenReturn(userList);

        // 发起 GET 请求，获取联系人列表
        mockMvc.perform(post("/contact/list")
                        .param("uid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uid").value(2))
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    public void testDeleteContactByUid_Success() throws Exception {
        // 模拟 contactService 的行为
        when(contactService.deleteContactByUid(anyInt(), anyInt())).thenReturn(true);

        // 发起 DELETE 请求，删除联系人
        mockMvc.perform(post("/contact/delete")
                        .param("uid1", "1")
                        .param("uid2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testDeleteContactByUid_Failure() throws Exception {
        // 模拟 contactService 的行为
        when(contactService.deleteContactByUid(anyInt(), anyInt())).thenReturn(false);

        // 发起 DELETE 请求，删除联系人
        mockMvc.perform(post("/contact/delete")
                        .param("uid1", "1")
                        .param("uid2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
