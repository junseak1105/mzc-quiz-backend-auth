package com.mzc.quiz.play.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostController {

    @Autowired
    private HostService hostService;

    // CREATEPLAY
    @PostMapping("/v1/host/createPlay")
    public DefaultRes createPlay(@RequestBody QuizMessage quizMessage){
        return hostService.createPlay(quizMessage.getQuizId());
    }

    // START
    // - 클라이언트에 퀴즈 시작 명령어 전송
    @MessageMapping("/START/{pin}")
    public void quizStart(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){
        hostService.quizStart(pin, quizMessage);
    }

    // RESULT
    // - 클라이언트에 현재 퀴즈 결과 명령어 전송
    @MessageMapping("/RESULT/{pin}")
    public void quizResult(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){
        hostService.quizResult(pin, quizMessage);
    }

    // NEXT
    // - 클라이언트에 다음문제로 이동 명령어 전송
    @MessageMapping("/NEXT/{pin}")
    public void quizNext(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){
        hostService.quizNext(pin, quizMessage);
    }

    // SKIP
    // - 클라이언트에 다음문제 스킵 명령어 전송
    @MessageMapping("/SKIP/{pin}")
    public void quizSkip(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){
        hostService.quizSkip(pin, quizMessage);
    }

    // FINAL
    // - 클라이언트에 최종 퀴즈 결과 명령어 전송
    @MessageMapping("/FINAL/{pin}")
    public void quizFinal(@DestinationVariable("pin") String pin, @RequestBody QuizMessage quizMessage){
        hostService.quizFinal(pin, quizMessage);
    }

    // BAN
    // - 특정 유저 강퇴
    @MessageMapping("/BAN/{pin}")
    @SendToUser("/queue/session")
    public void userBan(){
        hostService.userBan();
    }
}
