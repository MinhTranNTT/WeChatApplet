package com.zw.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zw.common.domain.User;
import com.zw.common.utils.TokenUtil;
import com.zw.mapper.UserMapper;
import com.zw.service.CouponService;
import com.zw.service.RedisService;
import com.zw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author aking
* @description 针对表【mall_user】的数据库操作Service实现
* @createDate 2022-12-20 09:34:47
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RedisService redisService;
    @Autowired
    CouponService couponService;

    // 用户登录
    @Override
    public String login(String code) {
        // code为前端传过来的登录数据，直接调用微信接口进行登录
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        String result = HttpUtil.get(replaceUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openid = jsonObject.get("openid").toString();
        String session_key = jsonObject.get("session_key").toString();
        // 生成token
        String token = tokenUtil.getToken(openid, session_key);

        // 判断用户是否存在
//        if(redisService.get(openid) == null){
        if (userMapper.checkUserExist(openid) == 0) {
            // 不存在，创建用户,并将openid存入redis
//            redisService.set(openid,openid);
            // 创建用户
            User user = new User();
            user.setOpenid(openid); // 设置openid
            user.setRegisterTime(new Date()); // 设置注册时间
            userMapper.insert(user);
            // 创建新人优惠券
            couponService.createNewerCoupon(openid);
        }else {
            // 存在，更新用户登录时间
            Date lastRegisterTime = userMapper.getRegisterTimeByOpenid(openid);
            User user = new User();
            user.setOpenid(openid);
            user.setLastRegisterTime(lastRegisterTime);
            System.out.println(lastRegisterTime);
            user.setRegisterTime(new Date());
            userMapper.updateUserByOpenid(user);
        }
        return token;
    }
}




