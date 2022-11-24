package com.mzc.redis.model;

import lombok.Data;

@Data
public class QuizPlayEntity {
    private int total_point;
    private int total_client;
    private int quiz_answer;
    private int quiz_wrong_answer;
    private String profile;
}
