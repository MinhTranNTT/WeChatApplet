package com.zw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zw.common.domain.User;

/**
* @author aking
* @description 针对表【mall_user】的数据库操作Service
* @createDate 2022-12-20 09:34:47
*/
public interface UserService extends IService<User> {
    //用户登录
    String login(String code);
}
