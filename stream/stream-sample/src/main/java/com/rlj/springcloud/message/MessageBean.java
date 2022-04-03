package com.rlj.springcloud.message;

import lombok.Data;

/**
 * @author Renlingjie
 * @name 消息接收体，简单一些，就一个字段
 * @date 2022-03-29
 */
@Data
public class MessageBean {
    // 生产者在发布消息的时候，生成的消息体，我们原封不动的去接收，传递给消费者即可
    private String payload;
}
