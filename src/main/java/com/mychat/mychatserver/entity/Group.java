package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("mygroups")
public class Group {
    @TableId(type = IdType.AUTO)
    private Integer groupid;
    private String groupname;
    private Integer ownerid;

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupid=" + groupid +
                ", groupname='" + groupname + '\'' +
                ", ownerid=" + ownerid +
                '}';
    }
}
