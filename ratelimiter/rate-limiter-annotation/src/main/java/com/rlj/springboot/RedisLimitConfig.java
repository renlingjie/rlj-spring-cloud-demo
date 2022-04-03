package com.rlj.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-15
 */
@Configuration
public class RedisLimitConfig {
    @Bean
    public DefaultRedisScript loadRedisScript(){
        DefaultRedisScript redisScript = new DefaultRedisScript();
        //Lua脚本的位置，放在resource中(即classPath），使用ClassPathResource，在里面放resource路径即可
        redisScript.setLocation(new ClassPathResource("ratelimiter.lua"));
        //Lua脚本是一串字符串，执行完我们期望它返回什么需要指定，这里返回布尔，告诉我是否限流
        redisScript.setResultType(java.lang.Boolean.class);
        return redisScript;
    }
}
