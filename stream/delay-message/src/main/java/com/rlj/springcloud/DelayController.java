package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
@RestController
@Slf4j
@EnableBinding(value = {DelayChannel.class})
public class DelayController {
    @Autowired
    private DelayChannel channel;

    @PostMapping("delayMessage")
    public void sendMessage(String body,Integer seconds){
        log.info("接收到请求，{}秒后发送消息",seconds);
        // 1、messaging.support.MessageBuilder
        // 2、withPayload里面也可以传一个类，消费者接收也就用这个类接收
        // 3、发送延迟消息，必须有setHeader("x-delay",seconds * 1000)
        channel.produce().send(MessageBuilder.withPayload(body).setHeader("x-delay",seconds * 1000).build());
    }
}
