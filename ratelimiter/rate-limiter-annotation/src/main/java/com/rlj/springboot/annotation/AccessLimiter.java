package com.rlj.springboot.annotation;

import java.lang.annotation.*;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-15
 */
@Target({ElementType.METHOD}) //方法级别生效，即只有方法才能用这个注解
@Retention(RetentionPolicy.RUNTIME)  //指定运行周期为"运行时"
@Documented
public @interface AccessLimiter {
    int limit() default 1;
    String methodeKey() default "";
}
