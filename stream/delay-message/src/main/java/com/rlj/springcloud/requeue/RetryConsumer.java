package com.rlj.springcloud.requeue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
@Slf4j
@EnableBinding(value = {RequeueChannel.class})
public class RetryConsumer {

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
}
