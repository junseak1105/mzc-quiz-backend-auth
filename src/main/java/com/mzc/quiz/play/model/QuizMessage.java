package com.mzc.quiz.play.model;

// WebSocket 통신용 Message model

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class QuizMessage {
    //공통
    private String pinNum;
    private QuizCommandType command;
    private QuizActionType action;
    private String nickName;//Ban, setNickname

    private List<UserRank> rank;

    //type에 따른 분기
    //COMMAND
    private Quiz quiz;
    //SUBMIT
    private Submit submit;
}


