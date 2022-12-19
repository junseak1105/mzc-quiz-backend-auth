package deprecated.show.response;

import deprecated.show.entity.QuizInfo;
import lombok.*;

@Deprecated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShowListRes {
    private String id;
    private QuizInfo quizInfo;
}
