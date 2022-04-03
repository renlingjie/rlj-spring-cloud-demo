package com.rlj.springcloud;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Renlingjie
 * @name
 * @date 2022-03-16
 */
@Slf4j
@Service
public class JwtService {
    // 这个key非常重要，开发人一般员是接触不到的，会在外部加密后再给开发人员，这里就直接明文定义一个
    private static final String KEY = "rljkey";
    // 为了代码的健壮性，即使外界破解了上面的key，没有ISSURE也不行(其实也需要加密，这里也是明文定义)
    private static final String ISSURE = "rlj";
    // 过期时间60s
    private static final long TOKEN_EXPIRE_TIME = 60000;

    private static final String USER_NAME=  "username";


    /**
     * 加密算法生成Token(但凡调用这个方法的，说明Login都验证成功了)
     * @param account
     * @return
     */
    public String generateToken(Account account){
        Date now = new Date();
        // 根据这个key生成一个加密算法
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        String token = JWT.create().withIssuer(ISSURE).
                withIssuedAt(now).
                withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .withClaim(USER_NAME,account.getUsername()).sign(algorithm);
        log.info("Jwt generated user = {}",account.getUsername());
        return token;
    }

    /**
     * 校验Token：验证传入的Token是否是当前用户的
     * @param token
     * @param username
     * @return
     */
    public boolean verify(String token,String username){
        log.info("verifying jwt, username = {}",username);
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            // 前面定义的东西都可以去验证。这里依次验证：ISSURE是否是上面的ISSURE、Token里面的username是否是传入的username
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSURE).withClaim(USER_NAME,username).build();
            // 开始验证(不用再return了，因为在验证过程中有任何异常，都会被捕捉然后返回false，如果一切没问题自然走到最后返回true)
            verifier.verify(token);
        } catch (Exception e) {
            log.error("auth failed",e);
            return false;
        }
        return true;
    }
}
