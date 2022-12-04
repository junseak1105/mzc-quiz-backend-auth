package com.mzc.quiz.play.service;

import com.google.gson.Gson;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import com.mzc.quiz.play.model.Quiz;
import com.mzc.quiz.play.model.QuizActionType;
import com.mzc.quiz.play.model.QuizCommandType;
import com.mzc.quiz.play.model.QuizMessage;
import com.mzc.quiz.play.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RedisUtil redisUtil;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public DefaultRes joinRoom(QuizMessage quizMessage) {
        String pin = redisUtil.genKey(quizMessage.getPinNum());
        if (redisUtil.hasKey(pin)) {
            return DefaultRes.res(StatusCode.OK, ResponseMessages.SUCCESS, quizMessage);
        } else {
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessages.BAD_REQUEST);
        }
    }


    public void setNickname(Principal principal, QuizMessage quizMessage) {
        String playKey = redisUtil.genKey(quizMessage.getPinNum());
        String username = quizMessage.getNickName();
        // Set 조회해서 -> content에 넣어서 보내기

        System.out.println(quizMessage);
        if (redisUtil.SISMEMBER(playKey, username)) {
            /**
             * 닉네임 중복 처리 필요
             */
//            simpMessagingTemplate.convertAndSendToUser(username, "/queue/", quizMessage);
            System.out.println("닉네임 중복");
        } else {
            redisUtil.SADD(playKey, username);
            quizMessage.setAction(QuizActionType.COMMAND);
            quizMessage.setCommand(QuizCommandType.WAIT);
            // 보낸 유저한테만 다시 보내주고
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/" + quizMessage.getPinNum(), quizMessage);
            // 변경된 유저 목록은 브로드캐스트로 알려주고
            simpMessagingTemplate.convertAndSend("/pin/" + quizMessage.getPinNum(), "123123");
        }
    }

    public void submit(QuizMessage quizMessage) {
        String quizKey = redisUtil.genKey("META", quizMessage.getPinNum());

        String QuizDataToString = new String(Base64.getDecoder().decode(redisUtil.GetHashData(quizKey, "P" + quizMessage.getSubmit().getQuizNum()).toString()));
        Gson gson = new Gson();
        Quiz quiz = gson.fromJson(QuizDataToString, Quiz.class);

        //계산식: [ (TotalTime - 걸린시간) / TotalTime ] * 1000 * Rate * IsCorrect(0 or 1)
        double TotalTime = quiz.getTime();
        double AnswerTime = Integer.parseInt(quizMessage.getSubmit().getAnswerTime());
        double Rate = (int) quiz.getRate();

        //get rid of [ and ] in string
        String answer = quiz.getAnswer().toString().substring(1, quiz.getAnswer().toString().length() - 1);
        String[] answer_arr = answer.split(", ");

        int isCorrect = 0;
        if (quizMessage.getSubmit().getAnswer().length == answer_arr.length) {
            for (int i = 0; i < quizMessage.getSubmit().getAnswer().length; i++) {
                for(int j = 0; j < answer_arr.length; j++) {
                    if (quizMessage.getSubmit().getAnswer()[i].equals(answer_arr[j])) {
                        isCorrect = 1;
                        break;
                    }else if(j == answer_arr.length - 1) {
                        isCorrect = 0;
                    }
                }
                isCorrect = 1;
            }
        }

        double Score = ((TotalTime - AnswerTime) / TotalTime) * 1000 * Rate * isCorrect;

        // Result:키값 시작할 때 먼저 생성해놓는게 좋겠죠?
        // 랭킹점수 증가
        String resultKey = redisUtil.genKey("RESULT", quizMessage.getPinNum());
        // 해당 키가 존재하는지 체크
        if(redisUtil.hasKey(resultKey)){ // 있으면 점수 증가
            redisUtil.plusScore(resultKey, quizMessage.getNickName(), Score);
        }
        else{ // 없으면
            redisUtil.setZData(resultKey, quizMessage.getNickName(), Score);
        }
    }
}
