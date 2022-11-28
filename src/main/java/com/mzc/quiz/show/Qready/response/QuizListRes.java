package com.mzc.quiz.show.Qready.response;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.QuizInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizListRes {

    private String id;
    private QuizInfo quizInfo;
    private List<Quiz> quizList;

}
