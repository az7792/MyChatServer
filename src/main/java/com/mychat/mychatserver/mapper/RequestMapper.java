package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RequestMapper {

    @Insert("INSERT INTO requests (requester_id, target_user_id, target_group_id, message, created_at, request_type, approved) VALUES (#{requesterId}, #{targetUserId}, #{targetGroupId}, #{message}, #{createdAt}, #{requestType}, #{approved})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "request_id")
    int insertRequest(Request request);

    @Select("SELECT COUNT(*) > 0 FROM contact WHERE (uid1 = #{requesterId} AND uid2 = #{targetUserId}) OR (uid1 = #{targetUserId} AND uid2 = #{requesterId})")
    boolean isFriend(@Param("requesterId") Integer requesterId, @Param("targetUserId") Integer targetUserId);

    @Select("SELECT * FROM requests WHERE target_user_id = #{targetUserId}")
    List<Request> getRequestsByTargetUserId(@Param("targetUserId") Integer targetUserId);

    @Update("UPDATE requests SET approved = #{approved} WHERE request_id = #{id}")
    int updateRequestApproval(@Param("id") Integer requestId, @Param("approved") Boolean approved);

    @Delete("DELETE FROM requests WHERE request_id = #{id}")
    int deleteRequest(@Param("id") Integer requestId);

    @Select("SELECT COUNT(*) > 0 FROM requests WHERE requester_id = #{requesterId} AND target_user_id = #{targetUserId} AND request_type = 'FRIEND' AND approved = false")
    boolean isFriendRequestExist(@Param("requesterId") Integer requesterId, @Param("targetUserId") Integer targetUserId);
}
