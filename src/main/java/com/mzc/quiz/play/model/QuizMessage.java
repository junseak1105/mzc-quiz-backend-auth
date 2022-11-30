package com.mzc.quiz.play.model;

// WebSocket 통신용 Message model

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class QuizMessage {
    private String pinNum;
    private String quizId;
    private QuizMessageType command;
    private String sender;
    private String quizNum;
    private Object content;
}


