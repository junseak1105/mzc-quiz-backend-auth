package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.service.Test_WS_RedisService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class Test_WS_RedisController {

    @Autowired
    Test_WS_RedisService testWSRedisService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/session/test")
    @SendToUser("/queue/session")
    public String sesionTest(){
        log.info("session/Test");
        return "private message";
    }

}
