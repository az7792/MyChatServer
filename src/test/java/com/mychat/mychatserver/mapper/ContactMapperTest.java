package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Contact;
import com.mychat.mychatserver.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional // 测试完成后自动撤销修改，避免真正地写入数据到数据库中
public class ContactMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Test
    public void testAddContact() {
        // 插入相关用户数据以满足外键约束
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password123");
        userMapper.addUser(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password123");
        userMapper.addUser(user2);

        // 获取插入后的用户 UID
        User user1FromDb = userMapper.getUserByEmail(user1.getEmail());
        User user2FromDb = userMapper.getUserByEmail(user2.getEmail());

        // 添加联系人
        int result = contactMapper.addContactByUid(user1FromDb.getUid(), user2FromDb.getUid());
        assertTrue(result > 0, "联系人添加失败");

        // 检查联系人是否存在
        boolean isExist = contactMapper.isContactExist(user1FromDb.getUid(), user2FromDb.getUid());
        assertTrue(isExist, "联系人应已存在");

        // 获取联系人
        List<Contact> contacts = contactMapper.selectByUid(user1FromDb.getUid());
        assertThat(contacts).isNotEmpty();

        // 删除联系人
        Integer deleteResult = contactMapper.deleteContactById(user1FromDb.getUid(), user2FromDb.getUid());
        assertTrue(deleteResult > 0, "联系人删除失败");

        // 检查联系人是否存在
        isExist = contactMapper.isContactExist(user1FromDb.getUid(), user2FromDb.getUid());
        assertTrue(!isExist, "联系人应不存在");
    }
}
