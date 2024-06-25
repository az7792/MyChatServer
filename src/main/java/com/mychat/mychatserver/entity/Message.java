package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mychat.mychatserver.utils.JsonUtil;

import java.time.LocalDateTime;

@TableName("messages")
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer messageId;
    private Integer fromUserUid;
    private Integer toReceiver;
    private String receiverType;
    private String text;
    private LocalDateTime sentTime;
    private String type;
    public Message(){}
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getFromUserUid() {
        return fromUserUid;
    }

    public void setFromUserUid(Integer fromUserUid) {
        this.fromUserUid = fromUserUid;
    }

    public Integer getToReceiver() {
        return toReceiver;
    }

    public void setToReceiver(Integer toReceiver) {
        this.toReceiver = toReceiver;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //转换为JSON
    public String toJSON() {
        return JsonUtil.toJSON(this);
    }

}
