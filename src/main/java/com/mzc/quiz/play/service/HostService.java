package com.mzc.quiz.play.service;

import com.google.gson.Gson;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.Quiz;
import com.mzc.quiz.play.model.QuizActionType;
import com.mzc.quiz.play.model.QuizCommandType;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.repository.QplayRepository;
import com.mzc.quiz.play.util.RedisUtil;
import com.mzc.quiz.show.entity.Show;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class HostService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    QplayRepository qplayRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void quizStart(QuizMessage quizMessage) {
        String quizKey = redisUtil.genKey("META", quizMessage.getPinNum());
        if(redisUtil.hasKey(quizKey)) {
            int currentQuiz = Integer.parseInt(redisUtil.GetHashData(quizKey,"currentQuiz").toString());

            //String quizData = redisUtil.GetHashData(quizKey,"p"+currentQuiz).toString();
            //System.out.println(quizData);
            String QuizDataToString = new String(Base64.getDecoder().decode(redisUtil.GetHashData(quizKey,"P"+currentQuiz).toString()));

            Gson gson = new Gson();
            Quiz quiz = gson.fromJson(QuizDataToString, Quiz.class);
            quiz.setAnswer(null);

            quizMessage.setAction(QuizActionType.COMMAND);
            quizMessage.setCommand(QuizCommandType.START);
            quizMessage.setQuiz(quiz);
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
        }else {

        }
    }

    public void quizResult(QuizMessage quizMessage){
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizSkip(QuizMessage quizMessage){
        String quizKey = redisUtil.genKey("META", quizMessage.getPinNum());

        String currentQuiz = Integer.toString(Integer.parseInt(redisUtil.GetHashData(quizKey,"currentQuiz").toString())+1);
        redisUtil.setHashData(quizKey, "currentQuiz", currentQuiz);

        quizMessage.setCommand(QuizCommandType.RESULT);
        quizMessage.setAction(QuizActionType.COMMAND);
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void quizFinal(QuizMessage quizMessage){
        simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), quizMessage);
    }

    public void userBan(QuizMessage quizMessage){
        String pin = quizMessage.getPinNum();
        String key = redisUtil.genKey("PLAY", pin);
        String nickname = quizMessage.getNickName();
        // Long srem = redisUtil.SREM(key, nickname);

        if(redisUtil.SREM(key, nickname) == 1){
            QuizMessage resMessage = new QuizMessage();
            resMessage.setPinNum(quizMessage.getPinNum());
            resMessage.setCommand(QuizCommandType.KICK);
            resMessage.setAction(QuizActionType.COMMAND);
            resMessage.setNickName(nickname);
            System.out.println(getUserList(pin));
            simpMessagingTemplate.convertAndSend("/pin/"+quizMessage.getPinNum(), resMessage) ;
        }else{
            System.out.printf("해당 key 값이 없습니다.");
            simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), "해당 key 값이 없습니다.");
        }
    }

    public String[] getUserList(String pinNum){
        Set<String> userList = redisUtil.SMEMBERS("PLAY:"+pinNum);
        String[] test = new String[31];
        userList.toArray(test);
        test[0] = "0";
        return test;
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
            String quizKey = redisUtil.genKey("META", pin);

            if( redisUtil.hasKey(playKey) ){
                // 다시 생성
            }else{
                Show show = qplayRepository.findShowById(quizId);
                Gson gson = new Gson();

                if(show != null){
                    redisUtil.setHashData(quizKey, "currentQuiz", "1");
                    redisUtil.setHashData(quizKey, "lastQuiz", Integer.toString(show.getQuizData().size()));
                    for(int i=0; i<show.getQuizData().size();i++){
                        String base64QuizData = Base64.getEncoder().encodeToString(gson.toJson(show.getQuizData().get(i)).getBytes());
                        redisUtil.setHashData(quizKey, "P"+(i+1) ,base64QuizData);
                    }
                }
                else{
                    //return "퀴즈데이터가 정상적으로 저장되지 않았습니다.";
                }

                redisUtil.SADD(playKey, quizId);
                redisUtil.expire(playKey, 12, TimeUnit.HOURS);  // 하루만 유지??
                break;
            }
        }
        return pin;
    }

    public boolean NullCheck(QuizMessage quizMessage){
//        if(quizMessage == null || quizMessage.getContent() == null){
//            return true;
//        }
        return false;
    }
}
