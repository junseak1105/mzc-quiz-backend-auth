package com.mzc.redis.controller;

import com.mzc.redis.model.AccessToken;
import com.mzc.redis.repository.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.SplittableRandom;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {
    private final AccessTokenRepository accessTokenRepository;
    private final RedisTemplate redisTemplate;

    @GetMapping("/")
    public String ok(){
        System.out.println("/");
        return "ok";
    }

    @GetMapping("/keys")
    public String keys(){
        Set<byte[]> keys = redisTemplate.keys("*");
        log.info("keys : " + keys.toString());
        return "keys";
    }

    @GetMapping("/save")
    public String save(){
        System.out.println("/save");
        String randomId = createId();

        AccessToken accessToken = AccessToken.builder()
                .id(randomId)
                .token("token")
                .expired(1000)
                .build();
        log.info(">>>>> [save] accessToken={}", accessToken);

        accessTokenRepository.save(accessToken);

        return "save";
    }

    @GetMapping("/get")
    public String get(){
        System.out.println("/get");
        String id = createId();
        return accessTokenRepository.findById(id)
                .map(AccessToken::getToken)
                .orElse("0");
    }

    @GetMapping("/get2")
    public String get2(){
        System.out.println("/get2");
        String id = createId();
        return accessTokenRepository.findById(id)
                .map(AccessToken::getToken)
                .orElse("0");
    }

    private String createId() {
        SplittableRandom random = new SplittableRandom();
        return String.valueOf(random.nextInt(1,1_000_000_000));
    }
}
