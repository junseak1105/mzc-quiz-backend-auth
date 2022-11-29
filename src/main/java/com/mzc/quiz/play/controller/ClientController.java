package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.model.websocket.QuizMessage;
import com.mzc.quiz.play.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ClientController {

    @Autowired
    ClientService clientService;


    @MessageMapping("/join/{pin}")
    public void joinPlay(@DestinationVariable("pin") String pin, @Payload QuizMessage quizMessage){
        // 조인 할때마다 현재 유저 리스트를 보내주자

    }

    @MessageMapping("/submit/{pin}")
    public void submitPerQuestion(@DestinationVariable("pin") int pin, @Payload QuizMessage quizMessage){
        // 답 제출
        // 이거 체점을 어디서 할건지, 프론트에서 하고 넘길건지, 아니면 백에서 할건지, 백에서 하는게 편하지 않을까?
        log.info(pin);
        log.info(quizMessage);
//        clientService.submitPerResult(pin, quizMessage);

    }

}
