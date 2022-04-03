package com.rlj.springcloud;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Renlingjie
 * @name
 * @date 2022-04-03
 */
public interface DeadLetterChannel {
    // 每个输出、输入通道都需要一个名字
    String INPUT = "deadletter-consumer";
    String OUTPUT = "deadletter-producer";

    @Input(INPUT)
    SubscribableChannel consume();
    @Output(OUTPUT)
    MessageChannel produce();
}
