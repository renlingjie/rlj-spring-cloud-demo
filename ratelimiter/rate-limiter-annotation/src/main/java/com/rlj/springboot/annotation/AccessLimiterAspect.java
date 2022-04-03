package com.rlj.springboot.annotation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Renlingjie
 * @name 专门为注解@AccessLimiter定义的切面流程
 * @date 2022-01-15
 */
@Aspect
@Component
@Slf4j
public class AccessLimiterAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisScript<Boolean> rateLimitLua;

    // 1、定义一个切面点，指定给什么切入下面代码，自然是有注解AccessLimiter的方法
    @Pointcut("@annotation(com.rlj.springboot.annotation.AccessLimiter)")
    public void cut(){}
    // 2、通过上面的cut()方法，捕获有此注解的方法时，预先执行的流程(即给这些方法切入以下代码)
    @Before("cut()")
    public void before(JoinPoint joinPoint){ // JoinPoint对象封装了要被切入方法的信息
        // 2.1、通过JoinPoint拿到被切入方法的特征签名，通过该签名可获取到目标方法名、所属类的Class等信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 2.2、通过当前被切入的方法，拿到该方法上的AccessLimiter注解(为了获取注解中用户传入的参数)
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        String key = annotation.methodeKey();
        Integer limit = annotation.limit();
        // 2.3、若用户在注解中未设置key，则根据一定规则自动生成一个(limit必须要指定，考虑到用户可能不指定，故在注解中指定一个默认值)
        if (StringUtils.isEmpty(key)){
            // 2.3.1、如果用户没传入key，调用反射方法java.lang.reflect.Method.getParameterTypes()，返回一个Class对象数组，
            // 它们以声明顺序表示由此Method对象表示的方法的形式参数类型。如果底层方法没有参数，则返回长度为0的数组。
            // 注：因为这个例子我们直接通过controller方法接收请求，该方法没有任何参数，所以下面的type是空数组
            Class[] type = method.getParameterTypes();
            // 2.3.2、之后拿到的方法信息的Class数组，根据如下规则生成一个key
            key = method.getClass() + method.getName();
            if (type != null){
                String paramTypes = Arrays.stream(type).map(Class::getName).collect(Collectors.joining(","));
                log.info("paramTypes:"+paramTypes);
                key += "#" + paramTypes;
            }
        }

        // 2.4、由Redis调用Lua脚本，执行限流策略
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
