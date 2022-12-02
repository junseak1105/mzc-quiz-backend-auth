package com.mzc.quiz.play.service;

import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.QuizActionType;
import com.mzc.quiz.play.model.QuizCommandType;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RedisUtil redisUtil;

    private final SimpMessagingTemplate simpMessagingTemplate;
    public DefaultRes joinRoom(QuizMessage quizMessage){
        String pin = redisUtil.genKey(quizMessage.getPinNum());
        if(redisUtil.hasKey(pin)){
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, quizMessage);
        }else{
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessages.BAD_REQUEST);
        }
    };

    public void setNickname(QuizMessage quizMessage){
        String playKey = redisUtil.genKey(quizMessage.getPinNum());
        String username = quizMessage.getNickName();
        // Set 조회해서 -> content에 넣어서 보내기

        System.out.println(quizMessage);
        if(redisUtil.SISMEMBER(playKey, username)){
            /**
             * 닉네임 중복 처리 필요
             */
//            simpMessagingTemplate.convertAndSendToUser(username, "/queue/", quizMessage);
            System.out.println("닉네임 중복");
        }else{
            redisUtil.SADD(playKey, username);
            quizMessage.setAction(QuizActionType.COMMAND);
            quizMessage.setCommand(QuizCommandType.WAIT);
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
        }
    }

    public void submit(QuizMessage quizMessage) {
        String AnsKey = redisUtil.genKey_ans(quizMessage.getPinNum(), quizMessage.getSubmit().getQuizNum());
        /**
         * 정답 처리 기능 제작 필요
         */
        int score = 100;
        redisUtil.setZData(AnsKey, quizMessage.getNickName(), score);
    }
}
