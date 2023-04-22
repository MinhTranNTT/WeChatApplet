package com.zw.backstage.controller;

import com.zw.common.domain.Admin;
import com.zw.common.utils.JsonResult;
import com.zw.common.utils.annotation.Authorize;
import com.zw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // 获取管理员信息
    @GetMapping("/list")
    @Authorize
    public JsonResult<List<Admin>> getAdminList(){
        return new JsonResult<>(200,adminService.list());
    }

    // 修改管理员信息
    @PutMapping("/update")
    @Authorize
    public JsonResult<String> updateAdmin(@RequestBody Admin admin){
        adminService.updateById(admin);
        return new JsonResult<>(200,"修改成功");
    }

    // 删除管理员
    @DeleteMapping("/delete")
    @Authorize
    public JsonResult<String> deleteAdmin(@RequestBody Admin admin){
        adminService.removeById(admin.getId());
        return new JsonResult<>(200,"删除成功");
    }

    // 添加管理员
    @PostMapping("/add")
    @Authorize
    public JsonResult<String> addAdmin(@RequestBody Admin admin){
        adminService.save(admin);
        return new JsonResult<>(200,"添加成功");
    }
}
