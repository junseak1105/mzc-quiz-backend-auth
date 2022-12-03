package com.mzc.quiz.play.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.service.ClientService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;


    @PostMapping("/joinroom")
    @ApiOperation(value = "방 입장" ,
            notes = "{pinNum : 방번호}")
    public DefaultRes joinRoom(@RequestBody QuizMessage quizMessage){
        return clientService.joinRoom(quizMessage);
    }

    @MessageMapping("/setnickname")
    public void setNickname( Principal principal, @RequestBody QuizMessage quizMessage){
        clientService.setNickname(principal, quizMessage);
    }

    @MessageMapping("/submit")
    public void submit(@RequestBody QuizMessage quizMessage){
        clientService.submit(quizMessage);
    }
}
