package com.rlj.springcloud.mytest;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
public interface MyTestChannel {
    // 每个输出、输入通道都需要一个名字
    String INPUT1 = "consumer1";
    String OUTPUT1 = "producer1";
    String OUTPUT2 = "producer2";

    @Input(INPUT1)
    SubscribableChannel consume1();
    @Output(OUTPUT1)
    MessageChannel produce1();
    @Output(OUTPUT2)
    MessageChannel produce2();
}
