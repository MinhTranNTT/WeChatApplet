package com.zw.service.impl;


import com.zw.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    // 设置缓存，带过期时间
    @Override
    public void setex(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
    }

    //设置缓存，不带过期时间
    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);
    }

    // 获取缓存
    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 检查是否存在
    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    // 删除
    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }
}
