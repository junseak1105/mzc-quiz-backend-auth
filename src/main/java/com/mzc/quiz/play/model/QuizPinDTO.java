package com.mzc.quiz.play.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class QuizPinDTO {
    private String pinNum;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static QuizPinDTO create(String name){
        QuizPinDTO pin = new QuizPinDTO();

        pin.pinNum = UUID.randomUUID().toString();
        return pin;
    }
}
