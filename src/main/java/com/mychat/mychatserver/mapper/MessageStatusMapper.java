package com.mychat.mychatserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.mychat.mychatserver.entity.MessageStatus;

import java.util.List;

@Mapper
public interface MessageStatusMapper {
    //插入一条消息状态
    @Insert("INSERT INTO message_status (uid,message_id,status) VALUES (#{uid}, #{messageId},#{status})")
    int insertMessageStatus(MessageStatus messageStatus);

    //更新状态
    @Update("UPDATE message_status SET status = #{status} WHERE uid = #{uid} AND message_id = #{messageId}")
    int updateMessageStatus(MessageStatus messageStatus);

    //根据uid和状态查询消息id列表
    @Select("SELECT message_id FROM message_status WHERE uid = #{uid} AND status = #{status}")
    List<Integer> getMessageIds(Integer uid, String status);
}
