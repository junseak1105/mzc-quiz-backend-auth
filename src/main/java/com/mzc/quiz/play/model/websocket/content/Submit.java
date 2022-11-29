package com.mzc.quiz.play.model.websocket.content;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Submit {
    private String selected;
    private String totalElapsedTime;
    private String ans;
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