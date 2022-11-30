package com.mzc.quiz.play.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostController {

    @Autowired
    private HostService hostService;

    // CREATEPLAY
    @PostMapping("/v1/host/createPlay")
    public /*DefaultRes*/void createPlay(@RequestBody QuizMessage quizMessage){
        System.out.println(hostService.createPlay(quizMessage.getQuizId()));
        //return hostService.createPlay(quizMessage.getQuizId());
    }

    // START
    @MessageMapping("/START/{pin}")
    public void quizStart(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){

    }

    // RESULT
    @MessageMapping("/RESULT/{pin}")
    public void quizResult(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){

    }

    // NEXT
    @MessageMapping("/NEXT/{pin}")
    public void quizNext(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){

    }

    // SKIP
    @MessageMapping("/SKIP/{pin}")
    public void quizSkip(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){

    }

    // FINAL
    @MessageMapping("/FINAL/{pin}")
    public void quizFinal(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){

    }

    // BAN
    @MessageMapping("/BAN/{pin}")
    public void userBan(){

    }
}
