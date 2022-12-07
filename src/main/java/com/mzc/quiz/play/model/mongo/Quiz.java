package com.mzc.quiz.play.model.mongo;

import com.mzc.quiz.show.entity.Choice;
import com.mzc.quiz.show.entity.Media;
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
