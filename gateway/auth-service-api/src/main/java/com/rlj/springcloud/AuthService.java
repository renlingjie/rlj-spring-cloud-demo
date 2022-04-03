package com.rlj.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-16
 */
@FeignClient("auth-service")
public interface AuthService {
    @PostMapping("login")
    @ResponseBody
    public AuthResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password);

    @GetMapping("verify")
    public AuthResponse verify(@RequestParam("token") String token,
                               @RequestParam("username") String username);

    // 当token快过期的时候，可以不用登录重新生成token，而是刷新token
    @PostMapping("refresh")
    @ResponseBody
    public AuthResponse refresh(@RequestParam("token") String token);
}
