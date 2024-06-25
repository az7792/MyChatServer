package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Message;
import com.mychat.mychatserver.entity.MessageStatus;
import com.mychat.mychatserver.mapper.MessageMapper;
import com.mychat.mychatserver.mapper.MessageStatusMapper;
import com.mychat.mychatserver.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageServiceImplTest {

    @Mock
    private MessageMapper messageMapper;

    @Mock
    private MessageStatusMapper messageStatusMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMessage() {//测试存消息
        Message message = new Message();
        when(messageMapper.insertMessage(message)).thenReturn(1);

        Integer result = messageService.saveMessage(message);

        assertEquals(1, result);
        verify(messageMapper, times(1)).insertMessage(message);
    }

    @Test
    void testSaveMessageStatus() {//测试存消息状态
        MessageStatus messageStatus = new MessageStatus();
        when(messageStatusMapper.insertMessageStatus(messageStatus)).thenReturn(1);

        Integer result = messageService.saveMessageStatus(messageStatus);

        assertEquals(1, result);
        verify(messageStatusMapper, times(1)).insertMessageStatus(messageStatus);
    }

    @Test
    void testUpdateMessageStatus() {//测试更新消息状态
        MessageStatus messageStatus = new MessageStatus();
        when(messageStatusMapper.updateMessageStatus(messageStatus)).thenReturn(1);

        Integer result = messageService.updateMessageStstus(messageStatus);

        assertEquals(1, result);
        verify(messageStatusMapper, times(1)).updateMessageStatus(messageStatus);
    }

    @Test
    void testGetMessage() {//测试根据消息Id获取消息
        Message message = new Message();
        when(messageMapper.getMessageByMessageId(1)).thenReturn(message);

        Message result = messageService.getMessage(1);

        assertEquals(message, result);
        verify(messageMapper, times(1)).getMessageByMessageId(1);
    }

    @Test
    void testGetMessages() {//测试根据消息Id列表获取消息列表
        Message message1 = new Message();
        Message message2 = new Message();
        List<Integer> messageIds = Arrays.asList(1, 2);

        when(messageMapper.getMessageByMessageId(1)).thenReturn(message1);
        when(messageMapper.getMessageByMessageId(2)).thenReturn(message2);

        List<Message> result = messageService.getMessage(messageIds);

        assertEquals(2, result.size());
        assertEquals(message1, result.get(0));
        assertEquals(message2, result.get(1));
        verify(messageMapper, times(1)).getMessageByMessageId(1);
        verify(messageMapper, times(1)).getMessageByMessageId(2);
    }

    @Test
    void testGetMessageIds() {//根据uid和消息状态获取消息id列表
        List<Integer> messageIds = Arrays.asList(1, 2, 3);
        when(messageStatusMapper.getMessageIds(1, "unread")).thenReturn(messageIds);

        List<Integer> result = messageService.getMessageIds(1, "unread");

        assertEquals(messageIds, result);
        verify(messageStatusMapper, times(1)).getMessageIds(1, "unread");
    }
}
