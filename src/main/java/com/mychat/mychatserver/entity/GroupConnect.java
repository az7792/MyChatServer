package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("groupconnect")
public class GroupConnect {

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private Integer groupid;

    private Integer uid;

    @Override
    public String toString() {
        return "Group{" +
                "GroupId=" + groupid +
                ", UserId='" + uid + '\'' +
                '}';
    }

}
