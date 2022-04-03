package com.rlj.springcloud.mytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
@RestController
public class MyTestSendController {
    @Autowired
    private MyTestChannel channel;

    @PostMapping("sendtest")
    public void sendMessage(String body){
        //messaging.support.MessageBuilder
        channel.produce2().send(MessageBuilder.withPayload(body).build());
    }
}
