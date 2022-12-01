package com.mzc.quiz.play.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.model.QuizMessageType;
import com.mzc.quiz.play.util.RedisUtil;
import com.mzc.quiz.show.entity.Quiz;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RedisUtil redisUtil;

    private final SimpMessagingTemplate simpMessagingTemplate;
    public DefaultRes joinRoom(QuizMessage quizMessage){
        String pin = redisUtil.genKey(quizMessage.getPinNum());
        if(redisUtil.hasKey(pin)){
            quizMessage.setQuizId(redisUtil.SMEMBERS(pin).toString());
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, quizMessage);
        }else{
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessages.BAD_REQUEST);
        }
    };

    public DefaultRes setNickname(QuizMessage quizMessage){
        String playKey = redisUtil.genKey(quizMessage.getPinNum());
        String username = quizMessage.getNickName();
        // Set 조회해서 -> content에 넣어서 보내기

        if(redisUtil.SISMEMBER(playKey, username)){
            // 닉네임 중복
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }else{
            redisUtil.SADD(playKey, username);
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, quizMessage);
        }
//        Set<String> userListSet = redisUtil.SMEMBERS(playKey);
    }
}
