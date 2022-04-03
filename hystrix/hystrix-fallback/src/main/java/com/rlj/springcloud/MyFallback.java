package com.rlj.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-05
 */
@Component
public class MyFallback implements HystrixService{
    @Override
    @HystrixCommand(fallbackMethod = "test")
    public String error() {
        //int i = 1/0;  用于触发降级的降级
        return "因为远程调用方法出错，转到此降级方法中，降级方法成功执行...";
    }

    @Override
    public String timeout(int timeout) {
        return "因为该方法执行时间超过Hystrix配置文件中指定的降级超时时间，转到此降级方法中，降级方法成功执行...";
    }

    public String test() {
        return "降价的降级触发";
    }

}
