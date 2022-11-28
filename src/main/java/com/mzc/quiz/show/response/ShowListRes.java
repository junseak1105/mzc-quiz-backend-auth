package com.mzc.quiz.show.response;

import com.mzc.quiz.show.entity.QuizInfo;
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
