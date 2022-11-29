package com.mzc.quiz.play.service;

import com.mzc.quiz.play.model.websocket.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClientService {

    private final RedisUtil redisUtil;


    public void joinPlay(String pin, QuizMessage quizMessage){

        String playKey = redisUtil.genKey(pin);
        String username = quizMessage.getSender();
        if(redisUtil.hasSetData(playKey, username)){
            //있으면 - Retry로 재시도
        }else{
            // 없으면
        }


    }


}
