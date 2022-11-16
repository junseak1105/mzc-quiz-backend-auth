package com.mzc.quiz.show.Qready.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("show")

public class Show {
    @Id
    private String _id;
    private ShowInfo showInfo;
    private List<Quiz> quizList;
}
