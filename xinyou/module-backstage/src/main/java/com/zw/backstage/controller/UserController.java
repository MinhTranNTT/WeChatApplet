package com.zw.backstage.controller;

import com.zw.common.domain.User;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 获取用户列表
    @GetMapping("/list")
    @Authorize
    public JsonResult<List<User>> getAllUser(){
        return new JsonResult(200,userService.list());
    }

    // 根据id删除用户
    @DeleteMapping("/delete/{id}")
    @Authorize
    public JsonResult<Void> delete(@PathVariable("id") Integer id){
        userService.removeById(id);
        return new JsonResult<>(200);
    }

    // 根据id修改用户
    @PutMapping("/update")
    @Authorize
    public JsonResult<Void> update(@RequestBody User user){
        userService.updateById(user);
        return new JsonResult<>(200);
    }

    // 添加用户
    @PostMapping("/add")
    @Authorize
    public JsonResult<Void> add(@RequestBody User user){
        userService.save(user);
        return new JsonResult<>(200);
    }
}
