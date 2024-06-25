package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 测试完成后自动回滚事务，避免实际修改数据库
public class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    public void testSelectById() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        Group retrievedGroup = groupMapper.selectById(group.getGroupid());
        assertThat(retrievedGroup).isNotNull();
        assertThat(retrievedGroup.getGroupname()).isEqualTo(group.getGroupname());
        assertThat(retrievedGroup.getOwnerid()).isEqualTo(group.getOwnerid());
    }

    @Test
    public void testSelectByName() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        List<Group> retrievedGroups = groupMapper.selectByName("Test Group");
        assertThat(retrievedGroups).isNotEmpty();
        assertThat(retrievedGroups.get(0).getGroupname()).isEqualTo(group.getGroupname());
        assertThat(retrievedGroups.get(0).getOwnerid()).isEqualTo(group.getOwnerid());
    }

    @Test
    public void testCreatGroup() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);

        int result = groupMapper.creatGroup(group);
        assertThat(result).isEqualTo(1);

        Group retrievedGroup = groupMapper.selectById(group.getGroupid());
        assertThat(retrievedGroup).isNotNull();
        assertThat(retrievedGroup.getGroupname()).isEqualTo(group.getGroupname());
        assertThat(retrievedGroup.getOwnerid()).isEqualTo(group.getOwnerid());
    }

    @Test
    public void testUpdateGroupName() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        int result = groupMapper.updateGroupName(group.getGroupid(), "Updated Group");
        assertThat(result).isEqualTo(1);

        Group updatedGroup = groupMapper.selectById(group.getGroupid());
        assertThat(updatedGroup).isNotNull();
        assertThat(updatedGroup.getGroupname()).isEqualTo("Updated Group");
    }

    @Test
    public void testDeleteByGroupId() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        int result = groupMapper.deleteByGroupId(group.getGroupid());
        assertThat(result).isEqualTo(1);

        Group deletedGroup = groupMapper.selectById(group.getGroupid());
        assertThat(deletedGroup).isNull();
    }

    @Test
    public void testIsGroupExist() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        boolean exists = groupMapper.isGroupExist(group.getGroupid());
        assertThat(exists).isTrue();

        groupMapper.deleteByGroupId(group.getGroupid());

        exists = groupMapper.isGroupExist(group.getGroupid());
        assertThat(exists).isFalse();
    }

    @Test
    public void testGetOwnerIdByGroupId() {
        Group group = new Group();
        group.setGroupname("Test Group");
        group.setOwnerid(1);
        groupMapper.creatGroup(group);

        Integer ownerId = groupMapper.getOwnerIdByGroupId(group.getGroupid());
        assertThat(ownerId).isEqualTo(1);
    }
}
