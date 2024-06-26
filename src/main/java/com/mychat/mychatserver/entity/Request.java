package com.mychat.mychatserver.entity;

import java.time.LocalDateTime;

public class Request {
    private Integer id; // 请求ID
    private Integer requesterId; // 请求者ID
    private Integer targetUserId; // 目标用户ID（好友请求）
    private Integer targetGroupId; // 目标群组ID（群组请求）
    private String message; // 请求消息
    private LocalDateTime createdAt; // 请求创建时间
    private String requestType; // 请求类型（好友请求或群组请求）
    private boolean approved; // 是否批准

    // getter 和 setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Integer requesterId) {
        this.requesterId = requesterId;
    }

    public Integer getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Integer targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getTargetGroupId() {
        return targetGroupId;
    }

    public void setTargetGroupId(Integer targetGroupId) {
        this.targetGroupId = targetGroupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requesterId=" + requesterId +
                ", targetUserId=" + targetUserId +
                ", targetGroupId=" + targetGroupId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", requestType='" + requestType + '\'' +
                ", approved=" + approved +
                '}';
    }
}
