package com.mzc.quiz.play.service;

import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.model.QuizMessageType;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClientService {

    private final RedisUtil redisUtil;

    private final SimpMessagingTemplate simpMessagingTemplate;


    public void joinPlay(String pin, QuizMessage quizMessage){
        QuizMessage resMessage = new QuizMessage();

        String playKey = redisUtil.genKey(pin);
        String username = quizMessage.getSender();
        // Set 조회해서 -> content에 넣어서 보내기


        if(redisUtil.SISMEMBER(playKey, username)){
            // 닉네임 중복
            resMessage.setCommand(QuizMessageType.RETRYJOIN);
        }else{
            redisUtil.SADD(playKey, username);
            resMessage.setCommand(QuizMessageType.JOINOK);
        }

        Set<String> userListSet = redisUtil.SMEMBERS(playKey);
        resMessage.setContent(userListSet);
        log.info("UserList");
        log.info(resMessage);

        simpMessagingTemplate.convertAndSend("/pin/"+pin, resMessage);

    }


}
