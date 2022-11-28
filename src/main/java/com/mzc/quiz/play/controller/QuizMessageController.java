package com.mzc.quiz.play.controller;

import com.mzc.quiz.play.model.QuizMessageDTO;
import com.mzc.quiz.play.repository.QuizplayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuizMessageController {
    private final SimpMessagingTemplate template;

    @Autowired
    private final QuizplayRepository quizplayRepository;

    /*@MessageMapping(value = "/quiz/in")
    public void QuizIn(@Payload QuizMessageDTO message){
        System.out.println("message: { Pin : " + message.getPinNum() + ", nick : " + message.getNick() + ", message : " + message.getMessage() + " }");
        message.setMessage(message.getPinNum() + "번 방 입장");
        template.convertAndSend("/sub/quiz-play/room/" + message.getPinNum(), message);
    }*/

    @MessageMapping(value = "/quiz/message")
    public void message(@Payload QuizMessageDTO message){
        System.out.println("message: " + message.toString());
        template.convertAndSend("/sub/quiz-play/room/" + message.getPinNum(), message);
    }
}
