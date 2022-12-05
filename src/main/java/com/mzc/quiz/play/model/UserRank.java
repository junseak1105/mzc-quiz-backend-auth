package com.mzc.quiz.play.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRank {
    int rank;
    String nickName;
    Double rankScore;
}
