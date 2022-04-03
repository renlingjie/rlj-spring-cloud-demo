package com.rlj.springcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-04
 */
@RestController
public class Controller implements IService{
    @Override
    @GetMapping("/sayHi")
    public String sayHi(){
        return "Hello, this is Eureka Client~";
    }

    @Override
    public Friend sayHiPost(@RequestBody Friend friend){
        friend.setName("Eureka Client ~");
        friend.setAddress("beijing");
        return friend;
    }
}
