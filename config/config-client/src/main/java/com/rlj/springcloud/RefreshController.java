package com.rlj.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/refresh")
@RefreshScope
public class RefreshController {
    @Value("${words}")
    private String words;

    @Value("${password}")
    private String password;

    @GetMapping("/password")
    public String getPassword(){
        return "密码是" + password;
    }

    @GetMapping("/words")
    public String getWords(){
        return words;
    }
}
