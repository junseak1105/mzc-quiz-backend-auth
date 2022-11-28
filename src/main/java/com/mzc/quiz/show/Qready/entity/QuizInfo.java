package com.mzc.quiz.show.Qready.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuizInfo {

    private String email;
    private String title;
    private String category;
    private List<String> tags;
    private String titleImg_origin;
    private String titleImg_thumb;
    private Date createDate;
    private Date lastModifyDate;
    private boolean isPulic;
    private String state;
}

