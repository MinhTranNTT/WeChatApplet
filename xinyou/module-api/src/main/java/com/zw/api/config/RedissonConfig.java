package com.zw.api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: JavaBasic
 * @description: redisson配置类
 * @packagename: com.zw.api.config
 * @auther: ZWei
 * @date: 2021/1/27 15:39
 **/
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private String database;

    @Bean
    public RedissonClient redissonClient(){
        Config config=new Config();
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password);
        config.useSingleServer().setDatabase(Integer.parseInt(database));
        return Redisson.create(config);
    }
}
