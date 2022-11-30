package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.model.websocket.QuizMessage;
import com.mzc.quiz.play.service.RedisTestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class RedisTestController {

    @Autowired
    RedisTestService redisTestService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/room/{pin}")
    public void WebsocketTest(@DestinationVariable("pin") int pin, @Payload QuizMessage quizMessage){
//        redisTestService.WebsocketTest(pin, quizMessage);
//        redisTestService.RedisTest(pin, quizMessage);
        redisTestService.RedisUtilTest(pin, quizMessage);
//        redisTestService.addMember();
    }

//    @MessageMapping("/session/test")
    @MessageMapping("/session/test")
    @SendToUser("/queue/session")
    public String sesionTest(){
        log.info("session/Test");
        return "private message";
//        simpMessagingTemplate.convertAndSendToUser("user", "/queue/reply", "message");
    }

}
