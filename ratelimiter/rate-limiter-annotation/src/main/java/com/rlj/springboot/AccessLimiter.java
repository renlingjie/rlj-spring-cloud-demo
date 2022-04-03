package com.rlj.springboot;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-14
 */
@Service
@Slf4j
@Deprecated //标识这个类已经被淘汰了，因为我们要进行其基于注解的开发
public class AccessLimiter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    // 传入需要限流的一类方法的特征，以及限制1s中之内可以通过多少个请求
    public void limiterAccess(String key,Integer limit){
        /**
         * 思路如下：
         *     I、请求Lua脚本，查看当前请求是否已经达到需要限流的阈值(通过返回true/false判断)
         *     II、如果达到了比如打印一下日志，或者抛出一个异常即可
         */
        boolean acquired = stringRedisTemplate.execute(
                rateLimitLua,            //Lua脚本的真身
                Lists.newArrayList(key), //Lua脚本中的Key列表，传入的要被限流的方法特征
                limit.toString()         //Lua脚本的value列表
        );
        if (!acquired){
            log.error("your access is blocked, key = {}",key);
            throw new RuntimeException("Sorry!Your Access Is Blocked!!");
        }
    }
}
