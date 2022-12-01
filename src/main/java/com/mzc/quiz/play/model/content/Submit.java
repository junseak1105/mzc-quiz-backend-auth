package com.mzc.quiz.play.model.content;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Submit {
    private String answerTime; // 문제를 푼 시간
    private String answer; // 유저가 선택한 답안
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