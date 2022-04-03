package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
@Slf4j
@EnableBinding(value = {RetryChannel.class,RequeueChannel.class,DeadLetterChannel.class})
public class RetryConsumer {
    @StreamListener(RetryChannel.INPUT)
    public void consumeMessage(Object message){
        log.info("接收到消息---{}",message);
        throw new RuntimeException();
    }

    @StreamListener(RequeueChannel.INPUT)
    public void consumeMessage1(Object message){
        log.info("接收到消息---{}",message);
        // 收到消息后停顿3s，如果不停顿直接抛异常，那么会迅速重新入队，然后再消费再抛异常再迅速重新入队
        // 那么这个消息可能在1s能被集群下的消费者消费几百次
        try{
            Thread.sleep(3000L);
        } catch (Exception e){}
        throw new RuntimeException();
    }

    @StreamListener(DeadLetterChannel.INPUT)
    public void consumeMessage2(Object message){
        log.info("接收到消息---{}",message);
        throw new RuntimeException();
    }

    @StreamListener(FallbackChannel.INPUT)
    public void consumeMessage3(Object message){
        log.info("接收到消息---{}",message);
        throw new RuntimeException();
    }

    // 上面的消费者方法抛出异常，且重试次数达到上限，就会转到该降级方法中处理。@ServiceActivator需绑定一个Channel名，
    // 系统自动为我们生成的信道名为：主题.消费组.errors 也就是fallback-topic.Group-fallback.errors
    @ServiceActivator(inputChannel = "fallback-topic.Group-fallback.errors")
    public void fallback(Message<?> message){
        log.info("进入fallback流程");
    }
}
