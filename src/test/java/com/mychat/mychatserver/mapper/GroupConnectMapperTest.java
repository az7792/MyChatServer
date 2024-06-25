package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional // 测试完成后自动撤销修改，避免真正地写入数据到数据库中
public class GroupConnectMapperTest {

    @Autowired
    private GroupConnectMapper groupConnectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    private Integer groupid;
    private Integer uid1;
    private Integer uid2;

    @BeforeEach
    public void setUp() {
        // Insert a group
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1); // Assume ownerid 1 exists in users table
        groupMapper.creatGroup(group);
        groupid = group.getGroupid();

        // Insert two users
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        userMapper.addUser(user1);
        uid1 = user1.getUid();

        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("test2@example.com");
        user2.setPassword("password2");
        userMapper.addUser(user2);
        uid2 = user2.getUid();
    }

    @Test
    public void testInsertGroupMember() {
        groupConnectMapper.insertGroupMember(groupid, uid1);

        boolean isMemberExist = groupConnectMapper.isMemberExist(groupid, uid1);
        assertTrue(isMemberExist, "The user should be a member of the group.");
    }

    @Test
    public void testSelectInGroupByUid() {
        groupConnectMapper.insertGroupMember(groupid, uid1);

        User user = groupConnectMapper.selectInGroupByUid(groupid, uid1);
        assertThat(user).isNotNull();
    }

    @Test
    public void testDeleteContactById() {
        groupConnectMapper.insertGroupMember(groupid, uid1);

        int deleteCount = groupConnectMapper.deleteContactById(groupid, uid1);
        assertTrue(deleteCount > 0, "The user should be removed from the group.");

        boolean isMemberExist = groupConnectMapper.isMemberExist(groupid, uid1);
        assertFalse(isMemberExist, "The user should no longer be a member of the group.");
    }

    @Test
    public void testIsMemberExist() {
        boolean isMemberExistBefore = groupConnectMapper.isMemberExist(groupid, uid1);
        assertFalse(isMemberExistBefore, "The user should not be a member of the group initially.");

        groupConnectMapper.insertGroupMember(groupid, uid1);

        boolean isMemberExistAfter = groupConnectMapper.isMemberExist(groupid, uid1);
        assertTrue(isMemberExistAfter, "The user should be a member of the group after insertion.");
    }

    @Test
    public void testGetAllUidBygroupid() {
        groupConnectMapper.insertGroupMember(groupid, uid1);
        groupConnectMapper.insertGroupMember(groupid, uid2);

        List<Integer> userIds = groupConnectMapper.getAllUidBygroupid(groupid);
        assertThat(userIds).containsExactlyInAnyOrder(uid1, uid2);
    }

    @Test
    public void testSelectAllGroupOfUser() {
        Integer anotherGroupId = groupid + 1; // Assume another group id
        Group anotherGroup = new Group();
        anotherGroup.setGroupname("Another Group");
        anotherGroup.setOwnerid(1); // Assume ownerid 1 exists in users table
        groupMapper.creatGroup(anotherGroup); // Insert another group

        groupConnectMapper.insertGroupMember(groupid, uid1);
        groupConnectMapper.insertGroupMember(anotherGroup.getGroupid(), uid1);

        List<Integer> groupIds = groupConnectMapper.selectAllGroupOfUser(uid1);
        assertThat(groupIds).containsExactlyInAnyOrder(groupid, anotherGroup.getGroupid());
    }

    @Test
    public void testSelectInGroupByName() {
        groupConnectMapper.insertGroupMember(groupid, uid1);

        List<User> users = groupConnectMapper.selectInGroupByName(groupid, "testuser1");
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getUsername()).isEqualTo("testuser1");
    }

    @Test
    public void testGetAllUserBygroupid() {
        groupConnectMapper.insertGroupMember(groupid, uid1);
        groupConnectMapper.insertGroupMember(groupid, uid2);

        List<User> users = groupConnectMapper.getAllUserBygroupid(groupid);
        assertThat(users).hasSize(2);
        assertThat(users).extracting("username").containsExactlyInAnyOrder("testuser1", "testuser2");
    }
}
