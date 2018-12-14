package com.github.haomao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * @Author:ggq
 * @Date:2018/12/13
 * @Description:
 */
@Configuration
public class RedisConfig {

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void initRedisTemplate(){
        //RedisTemplate会自动初始化StringRedisSerializer,所以这里直接获取
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }
}
