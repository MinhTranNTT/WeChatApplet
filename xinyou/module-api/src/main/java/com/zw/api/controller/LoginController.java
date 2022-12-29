package com.zw.api.controller;

import com.zw.common.utils.JsonResult;
import com.zw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public JsonResult<String> login(@RequestBody String code) {
        return new JsonResult<>(200,userService.login(code));
    }
}
