package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.model.websocket.QuizMessage;
import com.mzc.quiz.play.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @Autowired
    RedisTestService redisTestService;

    @MessageMapping("/room/{pin}")
    public void WebsocketTest(@DestinationVariable("pin") int pin, @Payload QuizMessage quizMessage){
//        redisTestService.WebsocketTest(pin, quizMessage);
//        redisTestService.RedisTest(pin, quizMessage);
        redisTestService.RedisUtilTest(pin, quizMessage);
//        redisTestService.addMember();
    }

}
