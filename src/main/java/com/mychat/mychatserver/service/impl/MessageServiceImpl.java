package com.mychat.mychatserver.service.impl;

import com.mychat.mychatserver.entity.Message;
import com.mychat.mychatserver.entity.MessageStatus;
import com.mychat.mychatserver.mapper.MessageMapper;
import com.mychat.mychatserver.mapper.MessageStatusMapper;
import com.mychat.mychatserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageStatusMapper messageStatusMapper;

    //插入一条消息
    @Override
    public Integer saveMessage(Message message) {
        return messageMapper.insertMessage(message);
    }

    //插入一条消息状态
    @Override
    public Integer saveMessageStatus(MessageStatus messageStatus) {
        return messageStatusMapper.insertMessageStatus(messageStatus);
    }

    //更新消息状态
    @Override
    public Integer updateMessageStstus(MessageStatus messageStatus) {
        return messageStatusMapper.updateMessageStatus(messageStatus);
    }

    //根据消息ID获取消息
    @Override
    public Message getMessage(Integer messageId) {
        return messageMapper.getMessageByMessageId(messageId);
    }

    //根据消息ID列表获取消息列表
    @Override
    public List<Message> getMessage(List<Integer> messageIds) {
        List<Message> messages = new ArrayList<>();
        for (Integer messageId : messageIds) {
            messages.add(getMessage(messageId));
        }
        return messages;
    }

    //根据uid和消息状态获取消息ID列表
    @Override
    public List<Integer> getMessageIds(Integer uid, String status) {
        return messageStatusMapper.getMessageIds(uid, status);
    }

}
