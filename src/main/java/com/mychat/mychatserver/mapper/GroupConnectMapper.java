package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.GroupConnect;
import org.apache.ibatis.annotations.*;
import com.mychat.mychatserver.entity.User;

import java.util.List;

@Mapper
public interface GroupConnectMapper {

    // 在群组中添加新用户
    @Insert("INSERT INTO groupconnect (groupid, uid) VALUES (#{groupid}, #{uid})")
    int insertGroupMember(@Param("groupid") Integer groupid, @Param("uid") Integer uid);

    // 在群组中以用户名查询用户
    @Select("SELECT u.uid, u.username FROM groupconnect gc JOIN users u ON gc.uid = u.uid WHERE gc.groupid = #{groupid} AND u.username = #{username}")
    List<User> selectInGroupByName(@Param("groupid") Integer groupid, @Param("username") String username);

    // 在群组中以Uid查询用户
    @Select("SELECT * FROM users WHERE uid IN (SELECT uid FROM groupconnect WHERE groupid = #{groupid} AND uid = #{uid})")
    User selectInGroupByUid(@Param("groupid") Integer groupid, @Param("uid") Integer uid);

    // 在群组中删除用户
    @Delete("DELETE FROM groupconnect WHERE groupid = #{groupid} AND uid = #{uid}")
    int deleteContactById(@Param("groupid") Integer groupid, @Param("uid") Integer uid);

    // 判断群组中是否存在该用户
    @Select("SELECT COUNT(*) > 0 FROM groupconnect WHERE groupid = #{groupid} AND uid = #{uid}")
    boolean isMemberExist(@Param("groupid") Integer groupid, @Param("uid") Integer uid);

    // 根据群id查找所有群与用户的关系
    @Select("SELECT * FROM groupconnect WHERE groupid = #{groupid}")
    List<GroupConnect> selectAllUserInGroup(@Param("groupid") Integer groupid);

    // 根据用户id查询所有所在群
    @Select("SELECT groupid FROM groupconnect WHERE uid = #{uid}")
    List<Integer> selectAllGroupOfUser(@Param("uid") Integer uid);

    // 根据群id查找所有用户uid
    @Select("SELECT uid FROM groupconnect WHERE groupid = #{groupid}")
    List<Integer> getAllUidBygroupid(@Param("groupid") Integer groupid);

    // 根据群id查找所有用户信息
    @Select("SELECT u.* FROM groupconnect gc JOIN users u ON gc.uid = u.uid WHERE gc.groupid = #{groupid}")
    List<User> getAllUserBygroupid(@Param("groupid") Integer groupid);
}
