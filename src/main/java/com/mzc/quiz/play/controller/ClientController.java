package com.mzc.quiz.play.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/joinroom")
    public DefaultRes joinRoom(@RequestBody QuizMessage quizMessage){
        return clientService.joinRoom(quizMessage);
    }

    @MessageMapping("/setnickname")
    public DefaultRes setNickname(@RequestBody QuizMessage quizMessage){
        return clientService.setNickname(quizMessage);
    }

//    @MessageMapping("/JOIN/{pin}")
//    public void joinPlay(@DestinationVariable("pin") String pin, @Payload QuizMessage quizMessage){
//        clientService.joinPlay(pin, quizMessage);
//    }

    @MessageMapping("/submit")
    public void submitPerQuestion(@Payload QuizMessage quizMessage){
        clientService.submitPerResult(quizMessage);
    }

}
