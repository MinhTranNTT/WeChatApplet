package com.zw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zw.common.domain.Admin;
import com.zw.mapper.AdminMapper;
import com.zw.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author anking
* @description 针对表【backstage_admin】的数据库操作Service实现
* @createDate 2023-01-01 13:12:41
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    // 登录
    @Override
    public Integer login(String username, String password) {
        // 得到用户名，根据用户名查询用户，判断用户是否存在，如果不存在，返回1
        // 1:用户名不存在 2:密码错误 3:登录成功
        Admin admin = adminMapper.selectOneByUsername(username);
        if (admin == null) {
            return 1;
        }
        // 判断密码是否正确，如果不正确，返回2
        if (!admin.getPassword().equals(password)) {
            return 2;
        }
        // 登录成功，返回3
        return 3;
    }
}




