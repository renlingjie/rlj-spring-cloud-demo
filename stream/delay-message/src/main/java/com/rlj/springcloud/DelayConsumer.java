package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
@Slf4j
@EnableBinding(value = {DelayChannel.class})
public class DelayConsumer {
    @StreamListener(DelayChannel.INPUT)
    public void consumeMessage(Object message){
        log.info("接收到消息---{}",message);
    }
}
