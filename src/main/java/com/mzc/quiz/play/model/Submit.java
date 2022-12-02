package com.mzc.quiz.play.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Submit {
    //로그용
    private String answerTime; //미제출자 -1
    private String[] answer; // 유저가 선택한 답안 ["num1", "num2", "num3"] ["O"] ["X"] ["답안"]
    private Boolean correct; // 정답 여부

    //저장용
    private int score; // 계산된 점수: 문제를 푼 시간 30초 10초 1000점
}

/*
1. Submit 메시지 양식
{
  "command": "submit",
  "sender": "알짤딱",
  "quizNum": "12",
  "content": {
    "selected": "1",
    "totalElapsedTime": "2.023",
    "ans": "2"
  }
}
 */
