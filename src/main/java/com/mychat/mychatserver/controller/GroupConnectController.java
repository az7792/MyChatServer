package com.mychat.mychatserver.controller;

import com.mychat.mychatserver.entity.Group;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.GroupConnectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "群组成员管理")
@RestController
public class GroupConnectController {

    @Autowired
    private GroupConnectService groupConnectService;

    @Operation(summary = "群组中加入新成员")
    @PostMapping("/addusertogroup")
    public Map<String, Object> addUserToGroup(@RequestParam Integer groupid, @RequestParam Integer userid) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupConnectService.addUserToGroup(groupid, userid);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据群id与用户id查询群组成员")
    @PostMapping("/selectuseringroup/uid")
    public User getContactByUid(@RequestParam Integer groupid, @RequestParam Integer userid) {
        return groupConnectService.getContactByUid(groupid, userid);
    }

    @Operation(summary = "根据群id与用户名查询群组成员")
    @PostMapping("/selectuseringroup/username")
    public List<User> getContactByName(@RequestParam Integer groupid, @RequestParam String username) {
        return groupConnectService.getContactByName(groupid, username);
    }

    @Operation(summary = "根据群id拉取所有用户Uid")
    @PostMapping("/getgroupmembers/uid")
    public List<Integer> getAllUidByGroupId(@RequestParam Integer groupid) {
        return groupConnectService.getAllUidByGroupId(groupid);
    }

    @Operation(summary = "根据群id拉取所有用户")
    @PostMapping("/getgroupmembers/user")
    public List<User> getAllUserByGroupId(@RequestParam Integer groupid) {
        return groupConnectService.getAllUserByGroupId(groupid);
    }

    @Operation(summary = "根据用户uid查询所有群")
    @PostMapping("/getgroups/uid")
    public List<Group> getAllGroupOfUser(@RequestParam Integer uid) {
        return groupConnectService.getAllGroupOfUser(uid);
    }

    @Operation(summary = "根据群组id与用户id删除用户")
    @PostMapping("/deletemember/uid")
    public Map<String, Object> deleteMemberByUid(@RequestParam Integer groupid, @RequestParam Integer uid) {
        Map<String, Object> response = new HashMap<>();
        boolean success = groupConnectService.deleteMemberByUid(groupid, uid);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据UID获取其所在群id")
    @PostMapping("/getGroupids/uid")
    public List<Integer> getAllGroupidByUid(@RequestParam Integer uid) {
        return groupConnectService.getAllGroupidByUid(uid);
    }
}
