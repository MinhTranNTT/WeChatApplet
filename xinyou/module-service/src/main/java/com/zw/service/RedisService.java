package com.zw.service;

public interface RedisService {

    /**
     * 保存属性,带有过期时间
     */
    void setex(String key,Object value,long expire);

    //    不带过期时间
    void set(String key,Object value);
    /**
     * 获取属性
     */
    Object get(String key);

    Boolean exists(String key);
    /**
     * 删除属性
     */
    Boolean del(String key);
}