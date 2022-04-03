package com.rlj.springcloud;

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
@EnableBinding(value = {TestChannel.class})
public class TestSendController {
    @Autowired
    private TestChannel channel;

    @PostMapping("tests")
    public void sendMessage(String body){
        //messaging.support.MessageBuilder
        channel.produce().send(MessageBuilder.withPayload(body).build());
    }
}
