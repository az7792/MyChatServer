package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Message;
import com.mychat.mychatserver.entity.MessageStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MessageStatusMapperTest {
    @Autowired
    private MessageStatusMapper messageStatusMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void testInsertMessageStatus() {
        //插入一条消息
        Message message = new Message();
        message.setText("Hello World");
        message.setFromUserUid(1);
        message.setType("text");
        message.setToReceiver(19);
        message.setReceiverType("user");
        messageMapper.insertMessage(message);


        MessageStatus messageStatus = new MessageStatus(19,message.getMessageId(),"unread");
        int res = messageStatusMapper.insertMessageStatus(messageStatus);
        assertTrue(res==1,"插入失败");

        //查询
        List<Integer> messageIds = messageStatusMapper.getMessageIds(19,"unread");
        assertTrue(messageIds.size()==1&& Objects.equals(messageIds.getFirst(), message.getMessageId()),"查询失败");

        //更新
        messageStatus.setStatus("read");
        res = messageStatusMapper.updateMessageStatus(messageStatus);

        List<Integer>messageIds2 = messageStatusMapper.getMessageIds(19,"read");
        assertTrue(messageIds2.size()==1,"更新失败");
    }

}
