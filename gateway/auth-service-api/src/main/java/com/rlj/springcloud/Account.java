package com.rlj.springcloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private String username;
    private String token;   // 访问用的密钥
    private String refreshToken;  // token如果快过期了，可以通过refreshToken生成一个新的token
}
