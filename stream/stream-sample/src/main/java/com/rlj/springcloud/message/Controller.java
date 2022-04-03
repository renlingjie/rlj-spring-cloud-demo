package com.rlj.springcloud.message;

import com.rlj.springcloud.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-30
 */
@RestController
@Slf4j
public class Controller {

    @Autowired
    private MyTopic producer;

    @PostMapping("send")
    public void sendMessage(String body){
        //messaging.support.MessageBuilder
        producer.output().send(MessageBuilder.withPayload(body).build());
    }
}
