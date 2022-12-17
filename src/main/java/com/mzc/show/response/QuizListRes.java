package com.mzc.show.response;

import com.mzc.show.entity.Quiz;
import com.mzc.show.entity.QuizInfo;
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
