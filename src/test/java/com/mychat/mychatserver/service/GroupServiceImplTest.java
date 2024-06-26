package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class GroupServiceImplTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private GroupConnectMapper groupConnectMapper;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsGroupExist() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);

        boolean exists = groupService.isGroupExist(1);

        assertThat(exists).isTrue();
        verify(groupMapper, times(1)).isGroupExist(1);
    }

    @Test
    public void testAddGroupById_Success() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        when(userMapper.isUserExist(anyInt())).thenReturn(true);
        when(groupMapper.creatGroup(any(Group.class))).thenAnswer(invocation -> {
            Group createdGroup = invocation.getArgument(0);
            createdGroup.setGroupid(1); // Simulate generated ID
            return 1;
        });
        when(groupConnectMapper.insertGroupMember(anyInt(), anyInt())).thenReturn(1);

        boolean success = groupService.addGroupById(group);

        assertThat(success).isTrue();
        verify(groupMapper, times(1)).creatGroup(any(Group.class));
        verify(groupConnectMapper, times(1)).insertGroupMember(anyInt(), anyInt());
    }

    @Test
    public void testAddGroupById_Failure() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        when(userMapper.isUserExist(anyInt())).thenReturn(true);
        when(groupMapper.creatGroup(any(Group.class))).thenReturn(0);

        boolean success = groupService.addGroupById(group);

        assertThat(success).isFalse();
        verify(groupMapper, times(1)).creatGroup(any(Group.class));
        verify(groupConnectMapper, never()).insertGroupMember(anyInt(), anyInt());
    }

    @Test
    public void testGetGroupByUid() {
        Group group = new Group();
        group.setGroupid(1);
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupMapper.selectById(1)).thenReturn(group);

        Group result = groupService.getGroupByUid(1);

        assertThat(result).isNotNull();
        assertThat(result.getGroupid()).isEqualTo(1);
        verify(groupMapper, times(1)).isGroupExist(1);
        verify(groupMapper, times(1)).selectById(1);
    }

    @Test
    public void testGetGroupByName() {
        Group group = new Group();
        group.setGroupid(1);
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        when(groupMapper.selectByName("Test Group")).thenReturn(Collections.singletonList(group));

        List<Group> groups = groupService.getGroupByName("Test Group");

        assertThat(groups).isNotEmpty();
        assertThat(groups.get(0).getGroupname()).isEqualTo("Test Group");
        verify(groupMapper, times(1)).selectByName("Test Group");
    }

    @Test
    public void testUpdateGroupName() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupMapper.updateGroupName(1, "Updated Group")).thenReturn(1);

        boolean success = groupService.updateGroupName(1, "Updated Group");

        assertThat(success).isTrue();
        verify(groupMapper, times(1)).isGroupExist(1);
        verify(groupMapper, times(1)).updateGroupName(eq(1), eq("Updated Group"));
    }

    @Test
    public void testDeleteGroupByUid() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupMapper.deleteByGroupId(1)).thenReturn(1);

        boolean success = groupService.deleteGroupByUid(1);

        assertThat(success).isTrue();
        verify(groupMapper, times(1)).isGroupExist(1);
        verify(groupMapper, times(1)).deleteByGroupId(1);
    }

    @Test
    public void testGetGroupAvatarByGid() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupMapper.getAvatarByUid(1)).thenReturn("path/to/avatar.jpg");

        String avatar = groupService.getGroupAvatarByGid(1);

        assertThat(avatar).isEqualTo("path/to/avatar.jpg");
        verify(groupMapper, times(1)).isGroupExist(1);
        verify(groupMapper, times(1)).getAvatarByUid(1);
    }

    @Test
    public void testUpdateGroupAvatarByGid() {
        when(groupMapper.isGroupExist(1)).thenReturn(true);
        when(groupMapper.updateAvatarByUid(1, "path/to/updated.jpg")).thenReturn(1);

        boolean success = groupService.updateGroupAvatarByGid(1, "path/to/updated.jpg");

        assertThat(success).isTrue();
        verify(groupMapper, times(1)).isGroupExist(1);
        verify(groupMapper, times(1)).updateAvatarByUid(eq(1), eq("path/to/updated.jpg"));
    }
}
