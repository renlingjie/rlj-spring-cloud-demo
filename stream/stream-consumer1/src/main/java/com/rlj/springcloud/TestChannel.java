package com.rlj.springcloud;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-31
 */
public interface TestChannel {
    // 每个输出、输入通道都需要一个名字
    String INPUT = "test-consumer";

    @Input(INPUT)
    SubscribableChannel consume();
}
