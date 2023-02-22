package com.zw.backstage.controller;

import com.zw.common.domain.Admin;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.TokenUtil;
import com.zw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/login")
    public JsonResult<String> login(@RequestBody Admin admin){
        // 从admin中获取用户名和密码
        String username = admin.getUsername();
        String password = admin.getPassword();
        // 调用service层的login方法，判断用户名和密码是否正确
        // 1:用户名不存在 2:密码错误 3:登录成功
        Integer result = adminService.login(username, password);
        if (result == 1) {
            return new JsonResult<>(1, "用户名不存在");
        }
        if (result == 2) {
            return new JsonResult<>(2, "密码错误");
        }
        // 否则登录成功，调用jwt工具类生成token
        String token = tokenUtil.getToken(username,password);
        return new JsonResult<>(3, token);
    }
}
