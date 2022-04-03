package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-02-04
 */
@RestController
@Slf4j
public class Controller {
    @Autowired
    private IService service;

    @GetMapping("sayHi")
    public String sayHi(){
        return service.sayHi();
    }
}
