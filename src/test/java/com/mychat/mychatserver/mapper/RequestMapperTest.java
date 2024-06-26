package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Request;
import com.mychat.mychatserver.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional // 测试完成后自动撤销修改，避免真正地写入数据到数据库中
public class RequestMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Test
    public void testRequestMapper() {
        // 插入相关用户数据以满足外键约束
        User requester = new User();
        requester.setUsername("requester");
        requester.setEmail("requester@example.com");
        requester.setPassword("password123");
        userMapper.addUser(requester);

        User targetUser = new User();
        targetUser.setUsername("targetUser");
        targetUser.setEmail("targetUser@example.com");
        targetUser.setPassword("password123");
        userMapper.addUser(targetUser);

        // 获取插入后的用户 UID
        User requesterFromDb = userMapper.getUserByEmail(requester.getEmail());
        User targetUserFromDb = userMapper.getUserByEmail(targetUser.getEmail());

        // 检查两个用户是否已经是好友
        boolean result = requestMapper.isFriend(requesterFromDb.getUid(), targetUserFromDb.getUid());
        assertTrue(!result, "用户不应该是好友");

        // 创建一个新的好友请求
        Request request = new Request();
        request.setRequesterId(requesterFromDb.getUid());
        request.setTargetUserId(targetUserFromDb.getUid());
        request.setMessage("Please add me as a friend");
        request.setCreatedAt(LocalDateTime.now());
        request.setRequestType("FRIEND");
        request.setApproved(true);  // 假设请求已经批准，成为好友

        int res = requestMapper.insertRequest(request);
        assertTrue(res == 1, "插入失败");

        // 插入联系表
        contactMapper.addContactByUid(requesterFromDb.getUid(), targetUserFromDb.getUid());

        // 再次检查两个用户是否已经是好友
        result = requestMapper.isFriend(requesterFromDb.getUid(), targetUserFromDb.getUid());
        assertTrue(result, "用户应该是好友");
    }
}
