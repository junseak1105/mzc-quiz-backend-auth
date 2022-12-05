package com.mzc.quiz.play.service;

import com.google.gson.Gson;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.*;
import com.mzc.quiz.play.repository.QplayRepository;
import com.mzc.quiz.play.util.RedisUtil;
import com.mzc.quiz.show.entity.Show;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
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
        if (redisUtil.hasKey(quizKey)) {
            int currentQuiz = Integer.parseInt(redisUtil.GetHashData(quizKey, "currentQuiz").toString());

            //String quizData = redisUtil.GetHashData(quizKey,"p"+currentQuiz).toString();
            //System.out.println(quizData);
            String QuizDataToString = new String(Base64.getDecoder().decode(redisUtil.GetHashData(quizKey, "P" + currentQuiz).toString()));

            Gson gson = new Gson();
            Quiz quiz = gson.fromJson(QuizDataToString, Quiz.class);
            quiz.setAnswer(null);

            quizMessage.setAction(QuizActionType.COMMAND);
            quizMessage.setCommand(QuizCommandType.START);
            quizMessage.setQuiz(quiz);
            simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), quizMessage);
        } else {

        }
    }

    public void quizResult(QuizMessage quizMessage) {

        String quizKey = redisUtil.genKey("LOG", quizMessage.getPinNum());
        redisUtil.leftPop(quizKey, 5); // List<V> leftPop(K key, long count) 사용하면 될듯

        // 정답 데이터 가져오기
        String quizKey_1 = redisUtil.genKey("META", quizMessage.getPinNum());
        int currentQuiz = Integer.parseInt(redisUtil.GetHashData(quizKey_1, "currentQuiz").toString());

        String QuizDataToString = new String(Base64.getDecoder().decode(redisUtil.GetHashData(quizKey_1, "P" + currentQuiz).toString()));

        Gson gson = new Gson();
        Quiz quiz = gson.fromJson(QuizDataToString, Quiz.class);
        quizMessage.setQuiz(quiz);

        // 랭킹 점수
        String resultKey = redisUtil.genKey("RESULT", quizMessage.getPinNum());
        long userCount = redisUtil.setDataSize(redisUtil.genKey("PLAY", quizMessage.getPinNum()));
        Set<ZSetOperations.TypedTuple<String>> ranking = redisUtil.getRanking(resultKey, 0, userCount);

        Iterator<ZSetOperations.TypedTuple<String>> iterRank = ranking.iterator();
        List<UserRank> RankingList = new ArrayList<>();
        int rank=1;
        while(iterRank.hasNext()){
            ZSetOperations.TypedTuple<String> rankData = iterRank.next();
            RankingList.add(new UserRank(rank, rankData.getValue(), rankData.getScore()));
            System.out.println("rank : "+rank+", NickName : "+ rankData.getValue()+", Score : "+ rankData.getScore());
            rank++;
        }

        System.out.println(RankingList.toArray().toString());

        quizMessage.setRank(RankingList);

        // 마지막 문제 체크해서 final로 이동해야함.
        // 아직 터짐
        redisUtil.setHashData(quizKey_1,"currentQuiz", Integer.toString(quizMessage.getQuiz().getNum()+1));

        quizMessage.setCommand(QuizCommandType.RESULT);
        quizMessage.setAction(QuizActionType.COMMAND);
        simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), quizMessage);
    }

    public void quizSkip(QuizMessage quizMessage) {
        String quizKey = redisUtil.genKey("META", quizMessage.getPinNum());

        int currentQuiz = Integer.parseInt(redisUtil.GetHashData(quizKey, "currentQuiz").toString());
        int lastQuiz = Integer.parseInt(redisUtil.GetHashData(quizKey,"lastQuiz").toString());

        System.out.println("last" + lastQuiz);

        if(currentQuiz < lastQuiz){
            System.out.println("current" + currentQuiz);
            currentQuiz++;
            redisUtil.setHashData(quizKey, "currentQuiz", Integer.toString(currentQuiz));
            quizStart(quizMessage);
        }else{
            quizFinal(quizMessage);
        }

//        quizMessage.setCommand(QuizCommandType.START);
//        quizMessage.setAction(QuizActionType.COMMAND);
//        simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), quizMessage);
    }

    public void quizFinal(QuizMessage quizMessage) {

        redisUtil.genKey("LOG", quizMessage.getPinNum());
        quizMessage.setCommand(QuizCommandType.FINAL);
        quizMessage.setAction(QuizActionType.COMMAND);
        simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), quizMessage);
    }

    public void userBan(QuizMessage quizMessage) {
        String pin = quizMessage.getPinNum();
        String key = redisUtil.genKey("PLAY", pin);
        String nickname = quizMessage.getNickName();
        System.out.printf(nickname);

        if (redisUtil.SREM(key, nickname) == 1) {
            QuizMessage resMessage = new QuizMessage();
            resMessage.setPinNum(quizMessage.getPinNum());
            resMessage.setCommand(QuizCommandType.KICK);
            resMessage.setAction(QuizActionType.COMMAND);
            resMessage.setNickName(nickname);
            System.out.println(getUserList(pin));
            simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), resMessage);
        } else {
            QuizMessage resMessage = new QuizMessage();
            resMessage.setPinNum(quizMessage.getPinNum());
            resMessage.setCommand(QuizCommandType.KICK);
            resMessage.setAction(QuizActionType.COMMAND);
            resMessage.setNickName(nickname);
            simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), resMessage + "존재하지 않습니다");
        }
    }

    public String[] getUserList(String pinNum) {
        Set<String> userList = redisUtil.SMEMBERS("PLAY:" + pinNum);
        String[] test = new String[31];
        userList.toArray(test);
        test[0] = "0";
        return test;
    }

    // 퀴즈 핀
    public DefaultRes createPlay(String quizId) {
        try {

            String pin = makePIN(quizId);
            System.out.println("createPlay : " + pin);
            // mongoDB 조회 -> 총 몇개인지 확인하고
            // 문제별 방도 생성을 해야 되나?
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, pin);
        } catch (Exception e) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.BAD_REQUEST);
        }
    }


    public String makePIN(String quizId) {
        String pin;

        while (true) {
            pin = RandomStringUtils.randomNumeric(6);
            String playKey = redisUtil.genKey(pin);
            String quizKey = redisUtil.genKey("META", pin);

            if (redisUtil.hasKey(playKey)) {
                // 다시 생성
            } else {
                Show show = qplayRepository.findShowById(quizId);
                Gson gson = new Gson();

                if (show != null) {
                    redisUtil.setHashData(quizKey, "currentQuiz", "1");
                    redisUtil.setHashData(quizKey, "lastQuiz", Integer.toString(show.getQuizData().size()));
                    for (int i = 0; i < show.getQuizData().size(); i++) {
                        String base64QuizData = Base64.getEncoder().encodeToString(gson.toJson(show.getQuizData().get(i)).getBytes());
                        redisUtil.setHashData(quizKey, "P" + (i + 1), base64QuizData);
                    }
                } else {
                    //return "퀴즈데이터가 정상적으로 저장되지 않았습니다.";
                }

                redisUtil.SADD(playKey, quizId);
                redisUtil.expire(playKey, 12, TimeUnit.HOURS);  // 하루만 유지??
                break;
            }
        }
        return pin;
    }

    public boolean NullCheck(QuizMessage quizMessage) {
//        if(quizMessage == null || quizMessage.getContent() == null){
//            return true;
//        }
        return false;
    }
}
