package com.mzc.quiz.play.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class HostService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void quizStart(String pin, QuizMessage quizMessage){

    }

    public void quizResult(String pin, QuizMessage quizMessage){

    }

    public void quizNext(String pin, QuizMessage quizMessage){

    }

    public void quizSkip(String pin, QuizMessage quizMessage){

    }

    // 퀴즈 핀
    public DefaultRes createPlay(String quizId){
        try{
            String pin = makePIN(quizId);
            System.out.println("createPlay : " + pin);
            // mongoDB 조회 -> 총 몇개인지 확인하고
            // 문제별 방도 생성을 해야 되나?
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, pin);
        }catch (Exception e){
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }


    public String makePIN(String quizId){
        String pin;
        while(true){
            pin = RandomStringUtils.randomNumeric(6);
            String playKey = redisUtil.genKey(pin);

            if( redisUtil.hasKey(playKey) ){
                // 다시 생성
            }else{
                redisUtil.SADD(playKey, quizId);
                redisUtil.expire(playKey, 12, TimeUnit.HOURS);  // 하루만 유지??
                break;
            }
        }
        return pin;
    }






}
