package com.mzc.quiz.play.service;

import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class Test_WS_RedisService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisUtil redisUtil;


    public void WebsocketTest(int pin, QuizMessage quizMessage){
       log.info("pin - "+pin);
       log.info("message : "+quizMessage);
    }

    public void RedisTest(int pin, QuizMessage quizMessage){
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String key = "pin:"+pin;
        String value = quizMessage.getNickName();
        vop.set(key, value);
//        log.info("quiz message : "+quizMessage.getContent());
        log.info("Redis get " + vop.get(key));
    }

    public void RedisUtilTest(int pin, QuizMessage quizMessage){
        String key = "UserList:"+pin;
        String value = quizMessage.getNickName();
        redisUtil.SET(key, value);

        log.info("Redis get :" + redisUtil.getData(key));
    }
}
