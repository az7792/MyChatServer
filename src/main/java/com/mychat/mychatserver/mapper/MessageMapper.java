package com.mychat.mychatserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.mychat.mychatserver.entity.Message;

@Mapper
public interface MessageMapper {
    //插入一条消息
    @Insert("INSERT INTO messages (from_user_uid, to_receiver, text, type, receiver_type) VALUES (#{fromUserUid}, #{toReceiver}, #{text}, #{type}, #{receiverType})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId", keyColumn = "message_id")
    Integer insertMessage(Message message);

    //根据ID查询一条消息
    @Select("SELECT * FROM messages WHERE message_id = #{messageId}")
    Message getMessageByMessageId(Integer messageId);
}
