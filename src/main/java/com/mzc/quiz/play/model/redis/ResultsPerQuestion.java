package com.mzc.quiz.play.model.redis;

import com.mzc.quiz.play.model.websocket.content.Submit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("ResultsPerQuestion")
public class ResultsPerQuestion {

    @Id
    private int Pin;

    private String Username;

    private String Question;

    private Submit submit;



}

