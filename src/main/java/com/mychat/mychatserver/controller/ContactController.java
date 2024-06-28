package com.mychat.mychatserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.mychat.mychatserver.entity.User;
import com.mychat.mychatserver.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "好友管理")
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Operation(summary = "根据uid为用户添加新联系人")
    @PostMapping("/add")
    public Map<String, Object> addContactById(@RequestParam Integer uid1, @RequestParam Integer uid2) {
        Map<String, Object> response = new HashMap<>();
        boolean success = contactService.addContactById(uid1, uid2);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据uid获取用户所有联系人")
    @PostMapping("/list")
    public List<User> getContactByUid(@RequestParam Integer uid) {
        return contactService.getContactByUid(uid);
    }

    @Operation(summary = "根据uid删除联系人")
    @PostMapping("/delete")
    public Map<String, Object> deleteContactByUid(@RequestParam Integer uid1, @RequestParam Integer uid2) {
        Map<String, Object> response = new HashMap<>();
        boolean success = contactService.deleteContactByUid(uid1, uid2);
        response.put("success", success);
        return response;
    }

    @Operation(summary = "根据uid获取用户所有联系人UID")
    @PostMapping("/uidList")
    public List<Integer> getIdsByUid(@RequestParam Integer uid) {
        return contactService.getIdsByUid(uid);
    }
}
