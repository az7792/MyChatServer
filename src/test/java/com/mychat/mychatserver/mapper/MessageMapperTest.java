package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MessageMapperTest {
    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void testMessageMapper() {
        Message message = new Message();
        message.setText("Hello World");
        message.setFromUserUid(1);
        message.setType("text");
        message.setToReceiver(2);
        message.setReceiverType("user");
        //插入
        int res = messageMapper.insertMessage(message);
        assertTrue(res==1,"插入失败");

        //查询
        Message tmp = messageMapper.getMessageByMessageId(message.getMessageId());
        assertTrue(tmp!=null,"查询为空");
        System.out.println(tmp.toJSON());
    }
}
