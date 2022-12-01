package com.mzc.quiz.play.model;

// WebSocket 통신용 Message model

import com.mzc.quiz.play.model.content.Ban;
import com.mzc.quiz.play.model.content.Submit;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class QuizMessage {
    private String pinNum;
    private String quizId;
    private QuizCommandType command;
    private String nickName;
    private String quizNum;
    private Submit submit;
    private Ban ban;
    private Object content;
}


