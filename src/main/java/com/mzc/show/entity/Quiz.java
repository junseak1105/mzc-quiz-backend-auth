package com.mzc.show.entity;

import lombok.Data;

import java.util.List;

@Data
public class Quiz {
    private int num;
    private String type;
    private String question;
    private Media media;
    private Choice choiceList;
    private List<String> answer;
    private int time;
    private boolean useScore;
    private double rate;
}
