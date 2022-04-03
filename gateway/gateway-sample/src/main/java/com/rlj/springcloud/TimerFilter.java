package com.rlj.springcloud;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-15
 */
// 自定义的过滤器，需要实现GatewayFilter接口，同时也最好实现Ordered，定义此过滤器
// 的优先级，优先级越高，值越低。值最低不超过Int的最小负数，最高不超过Int的最大正数
public class TimerFilter implements GatewayFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        return null;
    }

    @Override
    public int getOrder() {
        return 0; // 默认排序为0
    }
}
