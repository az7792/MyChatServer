package com.mychat.mychatserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.mychat.mychatserver.entity.Request;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.mapper.RequestMapper;
import com.mychat.mychatserver.mapper.UserMapper;
import com.mychat.mychatserver.mapper.GroupMapper;
import com.mychat.mychatserver.mapper.GroupConnectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "请求管理")
@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupConnectMapper groupConnectMapper;

    // 申请添加好友
    @Operation(summary = "发送好友请求")
    @PostMapping("/friend")
    public Map<String, Object> sendFriendRequest(@RequestParam Integer requesterId,
                                                 @RequestParam Integer targetUserId,
                                                 @RequestParam(required = false) String message) {
        Map<String, Object> response = new HashMap<>();

        // 检查请求者是否存在
        User requester = userMapper.getUserByUid(requesterId);
        if (requester == null) {
            response.put("success", false);
            response.put("message", "请求者用户不存在");
            return response;
        }

        // 检查目标用户是否存在
        User targetUser = userMapper.getUserByUid(targetUserId);
        if (targetUser == null) {
            response.put("success", false);
            response.put("message", "目标用户不存在");
            return response;
        }

        // 检查是否已经是好友
        boolean isFriend = requestMapper.isFriend(requesterId, targetUserId);
        if (isFriend) {
            response.put("success", false);
            response.put("message", "你们已经是好友");
            return response;
        }


        // 创建新的好友请求
        Request request = new Request();
        request.setRequesterId(requesterId);
        request.setTargetUserId(targetUserId);
        request.setMessage(message);
        request.setCreatedAt(LocalDateTime.now());

        request.setRequestType("FRIEND");
        request.setApproved(false);

        Integer requestResult = requestMapper.insertRequest(request);
        response.put("success", requestResult > 0);
        response.put("message", requestResult > 0 ? "好友请求已提交" : "好友请求提交失败");

        return response;
    }

    // 用户申请加入群组
    @Operation(summary = "申请加入群组")
    @PostMapping("/group")
    public Map<String, Object> applyToJoinGroup(@RequestParam Integer groupId,
                                                @RequestParam Integer userId,
                                                @RequestParam(required = false) String message) {
        Map<String, Object> response = new HashMap<>();

        // 检查群组是否存在
        boolean groupExists = groupMapper.isGroupExist(groupId);
        if (!groupExists) {
            response.put("success", false);
            response.put("message", "群组不存在");
            return response;
        }

        // 检查用户是否已经在群组中
        boolean isMember = groupConnectMapper.isMemberExist(groupId, userId);
        if (isMember) {
            response.put("success", false);
            response.put("message", "用户已经在群组中");
            return response;
        }

        // 检查申请用户是否存在
        User user = userMapper.getUserByUid(userId);
        if (user == null) {
            response.put("success", false);
            response.put("message", "用户不存在");
            return response;
        }
        // 查找群主ID
        Integer ownerId = groupMapper.getOwnerIdByGroupId(groupId);
        if (ownerId == null) {
            response.put("success", false);
            response.put("message", "无法找到群主");
            return response;
        }

        // 创建新的群组申请
        Request request = new Request();
        request.setRequesterId(userId);
        request.setTargetGroupId(groupId); // 对于群组请求，使用 targetGroupId
        request.setMessage(message);
        request.setTargetUserId(ownerId);
        request.setCreatedAt(LocalDateTime.now());
        request.setRequestType("GROUP");
        request.setApproved(false);

        int requestResult = requestMapper.insertRequest(request);
        response.put("success", requestResult > 0);
        response.put("message", requestResult > 0 ? "群组申请已提交" : "群组申请提交失败");

        return response;
    }
}
