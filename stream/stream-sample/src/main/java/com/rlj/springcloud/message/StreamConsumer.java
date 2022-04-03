package com.rlj.springcloud.message;

import com.rlj.springcloud.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-29
 */
@Slf4j
// 信道(Sink)就类似于之前的Topic
// 绑定信道，会将里面的信道加入到初始化流程中。这里我们使用的是Stream默认为我们声明的Sink
@EnableBinding(
        value = {Sink.class, MyTopic.class}
)
public class StreamConsumer {
    // 给一个方法通过@StreamListener绑定一个信道，之后这个方法就会监听指定的信道去消费里面
    // 的方法。这里指定的信道是Stream中默认为我们提供的一个名为input的信道(Sink.INPUT)
    @StreamListener(Sink.INPUT) // stream.messaging.Sink
    public void consume(Object payload){
        log.info("接收到上游传入的消息{}",payload);
    }

    @StreamListener(MyTopic.INPUT)
    public void consumeMyMessages(Object payload){
        log.info("接收到上游传入的消息{}",payload);
    }
}
