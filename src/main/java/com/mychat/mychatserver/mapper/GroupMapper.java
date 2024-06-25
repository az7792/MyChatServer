package com.mychat.mychatserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.mychat.mychatserver.entity.Group;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    @Select("SELECT * FROM mygroups WHERE groupid = #{groupid}")
    Group selectById(Integer groupid);

    @Select("SELECT * FROM mygroups WHERE groupname = #{groupname}")
    List<Group> selectByName(String groupname);

    @Insert("INSERT INTO mygroups (groupname, ownerid) VALUES (#{groupname}, #{ownerid})")
    @Options(useGeneratedKeys = true, keyProperty = "groupid")
    Integer creatGroup(Group group);

    @Update("UPDATE mygroups SET groupname = #{groupname} WHERE groupid = #{groupid}")
    Integer updateGroupName(Integer groupid, String groupname);

    @Delete("DELETE FROM mygroups WHERE groupid = #{groupid}")
    Integer deleteByGroupId(Integer groupid);

    @Select("SELECT COUNT(*) > 0 FROM mygroups WHERE groupid = #{groupid}")
    boolean isGroupExist(Integer groupid);

    @Select("SELECT ownerid FROM mygroups WHERE groupid = #{groupId}")
    Integer getOwnerIdByGroupId(Integer groupId);
}
