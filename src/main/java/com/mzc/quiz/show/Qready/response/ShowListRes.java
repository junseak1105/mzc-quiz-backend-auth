package com.mzc.quiz.show.Qready.response;

import com.mzc.quiz.show.Qready.entity.QuizInfo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowListRes {
    private String id;
    private QuizInfo quizInfo;
}
