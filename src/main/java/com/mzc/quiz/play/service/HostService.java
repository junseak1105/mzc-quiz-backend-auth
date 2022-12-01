package com.mzc.quiz.play.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import com.mzc.quiz.show.entity.Quiz;
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
        if(NullCheck(quizMessage)){
            quizMessage.setContent("start fail (null)");
            simpMessagingTemplate.convertAndSend("/pin/"+pin,quizMessage);
            return;
        }

        simpMessagingTemplate.convertAndSend("/pin/"+pin, quizMessage);
    }


    public void quizStart(QuizMessage quizMessage){
        if(NullCheck(quizMessage)){
            quizMessage.setContent("start fail (null)");
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(),quizMessage);
            return;
        }
        System.out.println();
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizResult(QuizMessage quizMessage){
        if(NullCheck(quizMessage)){
            quizMessage.setContent("start fail (null)");
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(),quizMessage);
            return;
        }

        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizNext(QuizMessage quizMessage){
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizSkip(QuizMessage quizMessage){
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizFinal(QuizMessage quizMessage){
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void userBan(){

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

    public boolean NullCheck(QuizMessage quizMessage){
        if(quizMessage == null || quizMessage.getContent() == null){
            return true;
        }
        return false;
    }
}
