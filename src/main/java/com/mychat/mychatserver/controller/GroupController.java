package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "群组管理")
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Operation(summary = "根据id查询群组是否存在")
    @PostMapping("/exists/groupid")
    public Map<String, Object> isGroupExist(Integer groupid) {
        Map<String, Object> response = new HashMap<>();
        response.put("exist", groupService.isGroupExist(groupid));
        return response;
    }

    @Operation(summary = "创建群组")
    @PostMapping("/creatgroup")
    public Map<String, Object> addGroupById(@RequestBody Group group) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupService.addGroupById(group);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据群id查询群组")
    @PostMapping("/selectgroup/uid")
    public Group getGroupByUid(Integer groupid) {
        return groupService.getGroupByUid(groupid);
    }

    @Operation(summary = "根据群名称查询群组")
    @PostMapping("/selectgroup/name")
    public List<Group> getGroupByName(String groupname) {
        return groupService.getGroupByName(groupname);
    }

    @Operation(summary = "修改群名称")
    @PostMapping("/updategroup")
    public Map<String, Object> updateGroupName(Integer groupid, String groupname) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupService.updateGroupName(groupid, groupname);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据群组id删除群组")
    @PostMapping("/deletegroup/uid")
    public Map<String, Object> deleteGroupByUid(Integer groupid) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupService.deleteGroupByUid(groupid);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据群组ID获取头像")
    @PostMapping("/getGroupAvatar")
    public Map<String, Object> getGroupAvatarByGid(Integer gid) {
        Map<String, Object> response = new HashMap<>();
        String avatar = groupService.getGroupAvatarByGid(gid);
        if (avatar == null) {
            response.put("success", false);
            response.put("message", "Group avatar not found");
        } else {
            response.put("success", true);
            response.put("avatar", avatar);
        }
        return response;
    }

    @Operation(summary = "根据群组ID更新头像")
    @PostMapping("/updateGroupAvatar")
    public Map<String, Object> updateGroupAvatarByGid(Integer gid, String avatar) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupService.updateGroupAvatarByGid(gid, avatar);
        response.put("success", success);
        return response;
    }
}
