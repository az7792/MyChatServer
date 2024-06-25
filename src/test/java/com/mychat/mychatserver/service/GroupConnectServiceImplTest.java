package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.impl.GroupConnectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GroupConnectServiceImplTest {

    @InjectMocks
    private GroupConnectServiceImpl groupConnectService;

    @Mock
    private GroupConnectMapper groupConnectMapper;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUserToGroup_Success() {
        when(groupMapper.isGroupExist(anyInt())).thenReturn(true);
        when(userMapper.isUserExist(anyInt())).thenReturn(true);
        when(groupConnectMapper.isMemberExist(anyInt(), anyInt())).thenReturn(false);
        when(groupConnectMapper.insertGroupMember(anyInt(), anyInt())).thenReturn(1);

        boolean result = groupConnectService.addUserToGroup(1, 1);

        assertTrue(result);
        verify(groupMapper, times(1)).isGroupExist(anyInt());
        verify(userMapper, times(1)).isUserExist(anyInt());
        verify(groupConnectMapper, times(1)).isMemberExist(anyInt(), anyInt());
        verify(groupConnectMapper, times(1)).insertGroupMember(anyInt(), anyInt());
    }

    @Test
    public void testAddUserToGroup_GroupNotExist() {
        when(groupMapper.isGroupExist(anyInt())).thenReturn(false);

        boolean result = groupConnectService.addUserToGroup(1, 1);

        assertFalse(result);
        verify(groupMapper, times(1)).isGroupExist(anyInt());
        verify(userMapper, never()).isUserExist(anyInt());
        verify(groupConnectMapper, never()).isMemberExist(anyInt(), anyInt());
        verify(groupConnectMapper, never()).insertGroupMember(anyInt(), anyInt());
    }

    @Test
    public void testAddUserToGroup_UserNotExist() {
        when(groupMapper.isGroupExist(anyInt())).thenReturn(true);
        when(userMapper.isUserExist(anyInt())).thenReturn(false);

        boolean result = groupConnectService.addUserToGroup(1, 1);

        assertFalse(result);
        verify(groupMapper, times(1)).isGroupExist(anyInt());
        verify(userMapper, times(1)).isUserExist(anyInt());
        verify(groupConnectMapper, never()).isMemberExist(anyInt(), anyInt());
        verify(groupConnectMapper, never()).insertGroupMember(anyInt(), anyInt());
    }

    @Test
    public void testGetContactByUid_Success() {
        User user = new User();
        user.setUid(1);
        user.setUsername("testuser");

        when(groupMapper.isGroupExist(anyInt())).thenReturn(true);
        when(userMapper.isUserExist(anyInt())).thenReturn(true);
        when(groupConnectMapper.isMemberExist(anyInt(), anyInt())).thenReturn(true);
        when(userMapper.getUserByUid(anyInt())).thenReturn(user);

        User result = groupConnectService.getContactByUid(1, 1);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
}
