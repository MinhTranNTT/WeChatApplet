package com.zw.api.controller;

import com.zw.common.domain.User;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.mapper.UserMapper;
import com.zw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    //获取用户信息
    @Authorize
    @GetMapping("/info")
    public JsonResult<User> getUserInfo(HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        User user = userMapper.getOneByOpenid(openid);
        return new JsonResult<>(200,user);
    }

    // 修改个人信息
    @Authorize
    @PostMapping("/update")
    public JsonResult<String> updateUserInfo(@RequestBody User user,HttpServletRequest request){
        String openid = request.getAttribute("openid").toString();
        user.setOpenid(openid);
        userMapper.updateUserByOpenid(user);
        return new JsonResult<>(200,"修改成功");
    }
}
