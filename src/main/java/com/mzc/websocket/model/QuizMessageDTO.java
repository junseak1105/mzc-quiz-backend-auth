package com.mzc.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuizMessageDTO {
    private String pinNum;
    private double nick;
    private String messageType;
    private String msg;
    private String point;
    private String answer;
    private String worng;
}
