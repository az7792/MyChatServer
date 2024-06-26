package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Contact;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.ContactMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContactServiceImplTest {

    @MockBean
    private ContactMapper contactMapper;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ContactServiceImpl contactService;

    @Test
    public void testAddContactById_Success() {
        Integer uid1 = 1;
        Integer uid2 = 2;

        when(contactMapper.isContactExist(uid1, uid2)).thenReturn(false);
        when(userMapper.isUserExist(uid1)).thenReturn(true);
        when(userMapper.isUserExist(uid2)).thenReturn(true);

        boolean result = contactService.addContactById(uid1, uid2);

        verify(contactMapper, times(1)).addContactByUid(uid1, uid2);
        assertTrue(result, "添加联系人成功的情况测试失败");
    }

    @Test
    public void testAddContactById_ContactExists() {
        Integer uid1 = 1;
        Integer uid2 = 2;

        when(contactMapper.isContactExist(uid1, uid2)).thenReturn(true);

        boolean result = contactService.addContactById(uid1, uid2);

        verify(contactMapper, never()).addContactByUid(anyInt(), anyInt());
        assertFalse(result, "联系人已存在的情况测试失败");
    }

    @Test
    public void testAddContactById_UserNotExist() {
        Integer uid1 = 1;
        Integer uid2 = 2;

        when(contactMapper.isContactExist(uid1, uid2)).thenReturn(false);
        when(userMapper.isUserExist(uid1)).thenReturn(false);

        boolean result = contactService.addContactById(uid1, uid2);

        verify(contactMapper, never()).addContactByUid(anyInt(), anyInt());
        assertFalse(result, "用户不存在的情况测试失败");
    }

    @Test
    public void testGetContactByUid() {
        Integer uid = 1;
        Integer uid2 = 2;
        Contact contact = new Contact();
        contact.setUid1(uid);
        contact.setUid2(uid2);
        User user = new User();
        user.setUid(uid2);

        when(contactMapper.selectByUid(uid)).thenReturn(Arrays.asList(contact));
        when(userMapper.isUserExist(uid2)).thenReturn(true);
        when(userMapper.getUserByUid(uid2)).thenReturn(user);

        List<User> result = contactService.getContactByUid(uid);

        assertEquals(1, result.size(), "获取联系人数量不正确");
        assertEquals(uid2, result.get(0).getUid(), "获取的联系人UID不正确");
        verify(contactMapper, times(1)).selectByUid(uid);
        verify(userMapper, times(1)).getUserByUid(uid2);
    }

    @Test
    public void testDeleteContactByUid_Success() {
        Integer uid1 = 1;
        Integer uid2 = 2;

        when(contactMapper.isContactExist(uid1, uid2)).thenReturn(true);
        when(contactMapper.isContactExist(uid2, uid1)).thenReturn(true);
        when(contactMapper.deleteContactById(uid1, uid2)).thenReturn(1);
        when(contactMapper.deleteContactById(uid2, uid1)).thenReturn(1);

        boolean result = contactService.deleteContactByUid(uid1, uid2);

        verify(contactMapper, times(1)).deleteContactById(uid1, uid2);
        verify(contactMapper, times(1)).deleteContactById(uid2, uid1);
        assertTrue(result, "删除联系人成功的情况测试失败");
    }

    @Test
    public void testDeleteContactByUid_ContactNotExist() {
        Integer uid1 = 1;
        Integer uid2 = 2;

        when(contactMapper.isContactExist(uid1, uid2)).thenReturn(false);

        boolean result = contactService.deleteContactByUid(uid1, uid2);

        verify(contactMapper, never()).deleteContactById(anyInt(), anyInt());
        assertFalse(result, "联系人不存在的情况测试失败");
    }
}
