package com.rlj.springcloud.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-30
 */
public interface MyTopic {
    String INPUT = "myTopic-consumer";
    String OUTPUT = "myTopic-producer";


    @Input(INPUT) // 消费者
    SubscribableChannel input();

    @Output(OUTPUT) // 生产者
    MessageChannel output();
}
