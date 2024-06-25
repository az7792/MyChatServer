package com.mychat.mychatserver.service;

import com.mychat.mychatserver.entity.Message;
import com.mychat.mychatserver.entity.MessageStatus;

import java.util.List;

public interface MessageService {
    Integer saveMessage(Message message);
    Integer saveMessageStatus(MessageStatus messageStatus);
    Integer updateMessageStstus(MessageStatus messageStatus);
    Message getMessage(Integer messageId);
    List<Message> getMessage(List<Integer> messageIds);
    List<Integer> getMessageIds(Integer uid,String status);
}
