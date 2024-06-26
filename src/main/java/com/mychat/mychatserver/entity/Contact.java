package com.mychat.mychatserver.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("contact")
public class Contact {


    public Integer getUid1() {
        return uid1;
    }

    public void setUid1(Integer uid1) {
        this.uid1 = uid1;
    }

    public Integer getUid2() {
        return uid2;
    }

    public void setUid2(Integer uid2) {
        this.uid2 = uid2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    private Integer uid1;
    private Integer uid2;

    private Integer status;



    @Override
    public String toString() {
        return "Group{" +
                ", Uid1='" + uid1 + '\'' +
                ", Uid2='" + uid2 + '\'' +
                '}';
    }
}
