package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.model.QuizMessage;
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


    @MessageMapping("/JOIN/{pin}")
    public void joinPlay(@DestinationVariable("pin") String pin, @Payload QuizMessage quizMessage){
        clientService.joinPlay(pin, quizMessage);
    }

    @MessageMapping("/submit/{pin}")
    public void submitPerQuestion(@DestinationVariable("pin") int pin, @Payload QuizMessage quizMessage){
//        clientService.submitPerResult(pin, quizMessage);

    }

}
