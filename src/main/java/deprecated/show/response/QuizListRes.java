package deprecated.show.response;

import deprecated.show.entity.Quiz;
import deprecated.show.entity.QuizInfo;
import lombok.*;

import java.util.List;

@Deprecated
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
