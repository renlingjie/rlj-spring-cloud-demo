package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-26
 */
@RestController
@Slf4j
public class Controller {
    // 下面的方法名叫什么无所谓，只要@XXXMapping中的路由，以及方法参数与@FeignClient接口的一致，就建立起了联系
    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hello, this is Eureka Client1~";
    }

    @PostMapping("/sayHiPost")
    public Friend sayHiPost(@RequestBody Friend friend){
        friend.setName("Eureka Client1 ~");
        friend.setAddress("beijing");
        return friend;
    }

    @GetMapping("/retry")
    public String retry(@RequestParam(name = "timeout") int timeout){
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "client1 retry success~";
    }

    @GetMapping("/error")
    public String error(){
        log.info("client1 is fail");
        throw new RuntimeException("error method");
    }

    @GetMapping("/timeout")
    public String timeout(@RequestParam(name = "timeout") int timeout){
        try {
            System.out.println(timeout * 1000);
            System.out.println("client1线程ID：" + Thread.currentThread().getId());
            Thread.sleep((long) timeout * 1000);
            System.out.println("client1 线程睡眠已经结束，继续执行了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "client1 has execute " + timeout + "s";
    }


}
