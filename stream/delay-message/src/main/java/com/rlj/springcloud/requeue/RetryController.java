package com.rlj.springcloud.requeue;

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
@EnableBinding(value = {RequeueChannel.class})
public class RetryController {

    @Autowired
    private RequeueChannel requeueChannel;

    @PostMapping("requeueMessage")
    public void sendMessage1(String body){
        log.info("生产者接收到要发送的消息--{}",body);
        // 1、messaging.support.MessageBuilder
        // 2、withPayload里面也可以传一个类，消费者接收也就用这个类接收
        requeueChannel.produce().send(MessageBuilder.withPayload(body).build());
    }
}
