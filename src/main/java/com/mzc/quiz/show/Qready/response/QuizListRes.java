package com.mzc.quiz.show.Qready.response;

import com.mzc.quiz.show.Qready.entity.Quiz;
import com.mzc.quiz.show.Qready.entity.ShowInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuizListRes {

    private String id;
    private ShowInfo showInfo;
    private List<Quiz> quizList;

}
