package com.rlj.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.rlj.springcloud.AuthResponseCode.SUCCESS;
import static com.rlj.springcloud.AuthResponseCode.USER_NOT_FOUND;

/**
 * @author Renlingjie
 * @name 假设用户名是rlj，密码是123
 * @date 2022-03-16
 */
@RestController
@Slf4j
public class Controller {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    @ResponseBody
    public AuthResponse lohin(@RequestParam String username,@RequestParam String password){
        Account account = Account.builder().username(username).build();
        if (!(username.equals("rlj") && password.equals("123"))){
            return AuthResponse.builder().code(USER_NOT_FOUND).build();
        }
        // 验证通过，则生成Token
        String token = jwtService.generateToken(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        redisTemplate.opsForValue().set(account.getRefreshToken(),account);

        return AuthResponse.builder().account(account).code(SUCCESS).build();
    }

    @PostMapping("/refresh")
    @ResponseBody
    public AuthResponse refreshToken(@RequestParam String refreshToken){
        // 注意：当使用Redis的opsForValue来存储对象的时候，这个对象必须是一个可被序列化的对象，所以Account要实现Serializable接口
        Account account = (Account) redisTemplate.opsForValue().get(refreshToken);
        if (account == null){
            AuthResponse.builder().code(USER_NOT_FOUND).build();
        }
        String token = jwtService.generateToken(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());
        // 使用新的Token，将当前account保存的设置进去
        redisTemplate.delete(refreshToken);
        redisTemplate.opsForValue().set(account.getRefreshToken(),account);
        return AuthResponse.builder().account(account).code(SUCCESS).build();
    }

    // 验证当前Token中的username和传入的username是否相同
    @GetMapping("/verify")  //因为虽然有参数，但并不会更改什么内容，就像查询一样，所以Get
    @ResponseBody
    public AuthResponse verify(@RequestParam String token,@RequestParam String username){
        boolean isSuccess = jwtService.verify(token, username);
        return AuthResponse.builder().code(isSuccess ? SUCCESS : USER_NOT_FOUND).build();
    }
}
