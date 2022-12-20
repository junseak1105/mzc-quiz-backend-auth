package com.mzc.Auth.config;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mzc.Auth.entity.Host;

import java.time.Instant;

public class JWTUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("test");
    private static final long AUTH_TIME = 20*60; // 20분
    private static final long REFRESH_TIME = 60*60*24*7; // 1주일

    public static String makeAuthToken(Host host){
        return JWT.create()
                .withSubject(host.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond()+AUTH_TIME)
                .sign(ALGORITHM);
    }

    public static String makeRefreshToken(Host host){
        return JWT.create()
                .withSubject(host.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond()+REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token){ // 인증키를 만들고 refresh 토큰 확인 하는 곳
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder().success(true)
                    .username(verify.getSubject()).build();
        }catch (Exception ex){
            // 실패시 실패한 username decode해서 값을 넘겨 줌
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .username(decode.getSubject()).build();
        }

    }
}
