package com.mzc.quiz.play.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    @Value("${spring.rabbitmq.host}")
    private String RabbitMQ_Host;
    @Value("${spring.rabbitmq.username}")
    private String RabbitMQ_ID;
    @Value("${spring.rabbitmq.password}")
    private String RabbitMQ_PW;
    @Value("${spring.rabbitmq.port}")
    private int RabbitMQ_Port;
    public static final String quizQueue = "quiz.queue.fanout";
    public static final String quieExchange = "q.exchange";

    @Bean
    public Queue quizQueue(){
        return new Queue(quizQueue, false);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(quieExchange);
    }

    @Bean
    Binding quizBinding(Queue quizQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(quizQueue).to(fanoutExchange);
    }


    @Bean
    public  com.fasterxml.jackson.databind.Module dateTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        //LocalDateTime serializable 을 위해
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
