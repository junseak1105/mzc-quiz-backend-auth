package com.mzc.redis.controller;


import com.mzc.redis.pub.RedisMessagePublisher;
import com.mzc.redis.repository.TestRepository;
import com.mzc.redis.sub.RedisMessageSubscriber;
import com.mzc.redis.model.QuizMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisMessagePublisher messagePublisher;

    @Autowired
    private TestRepository testRepository;


    @PostMapping("/publish")
    public void publish(@RequestBody QuizMessage quizMessage) {
        logger.info(">> publishing : {}", quizMessage);

        System.out.println(quizMessage);

        testRepository.save(quizMessage);

        messagePublisher.publish(quizMessage.toString());
    }

    @GetMapping("/subscribe")
    public List<String> getMessages(){
        return RedisMessageSubscriber.messageList;
    }

}