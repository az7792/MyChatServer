package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("message_status")
public class MessageStatus {
    private Integer uid;
    private Integer messageId;
    private String status;
    public MessageStatus(){}
    public MessageStatus(int uid, int messageId, String status) {
        this.uid = uid;
        this.messageId = messageId;
        this.status = status;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
