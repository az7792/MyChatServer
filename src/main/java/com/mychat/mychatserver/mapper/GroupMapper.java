package com.mychat.mychatserver.mapper;

import com.mychat.mychatserver.entity.Group;
import org.apache.ibatis.annotations.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<Group> {

    @Select("SELECT * FROM mygroups WHERE groupid = #{groupid}")
    Group selectById(Integer groupid);

    @Select("SELECT * FROM mygroups WHERE groupname = #{groupname}")
    List<Group> selectByName(String groupname);

    @Insert("INSERT INTO mygroups (groupname, ownerid, avatar) VALUES (#{groupname}, #{ownerid}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "groupid")
    Integer creatGroup(Group group);

    @Update("UPDATE mygroups SET groupname = #{groupname} WHERE groupid = #{groupid}")
    Integer updateGroupName(Integer groupid, String groupname);

    @Delete("DELETE FROM mygroups WHERE groupid = #{groupid}")
    Integer deleteByGroupId(Integer groupid);

    @Select("SELECT COUNT(*) > 0 FROM mygroups WHERE groupid = #{groupid}")
    boolean isGroupExist(Integer groupid);

    @Select("SELECT ownerid FROM mygroups WHERE groupid = #{groupid}")
    Integer getOwnerIdByGroupId(Integer groupid);

    @Select("SELECT avatar FROM mygroups WHERE groupid = #{groupid}")
    String getAvatarByUid(Integer groupid);

    @Update("UPDATE mygroups SET avatar = #{avatar} WHERE groupid = #{groupid}")
    int updateAvatarByUid(@Param("groupid") Integer groupid, @Param("avatar") String avatar);
}
