package com.mzc.quiz.play.service;

import com.mzc.quiz.play.model.websocket.QuizMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.mzc.quiz.play.config.RabbitConfig.quieExchange;
import static com.mzc.quiz.play.config.RabbitConfig.quizQueue;
import static com.mzc.quiz.play.config.StompWebSocketConfig.TOPIC;

@Service
public class RabbitmqService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publishQuizMessage(QuizMessage quizMessage){
        amqpTemplate.convertAndSend(quieExchange, "", quizMessage);
    }



}
