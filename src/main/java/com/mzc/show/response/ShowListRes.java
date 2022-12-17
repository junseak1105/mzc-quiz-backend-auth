package com.mzc.show.response;

import com.mzc.show.entity.QuizInfo;
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
