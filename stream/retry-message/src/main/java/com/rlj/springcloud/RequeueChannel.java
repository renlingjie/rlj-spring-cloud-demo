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
public interface RequeueChannel {
    // 每个输出、输入通道都需要一个名字
    String INPUT = "requeue-consumer";
    String OUTPUT = "requeue-producer";

    @Input(INPUT)
    SubscribableChannel consume();
    @Output(OUTPUT)
    MessageChannel produce();
}
