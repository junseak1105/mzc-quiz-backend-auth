package com.mzc.quiz.show.Qready.response;

import com.mzc.quiz.show.Qready.entity.ShowInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowListRes {
    private String id;
    private ShowInfo quizInfo;
}
