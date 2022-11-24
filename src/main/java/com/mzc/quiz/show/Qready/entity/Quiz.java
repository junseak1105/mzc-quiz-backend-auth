package com.mzc.quiz.show.Qready.entity;

import lombok.Data;

import java.util.List;

@Data
public class Quiz {
    private String num;
    private String type;
    private String question;
    private MediaInfo mediaInfo;
    private Choice choiceList;
    private List<String> answer;
    private int time;
    private boolean userScore;
    private double rate;
}
