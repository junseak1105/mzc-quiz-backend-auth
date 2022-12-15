package com.mzc.quiz.play.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    private static final String PIN_QUEUE_NAME = "quiz.queue";
    private static final String PIN_EXCHANGE_NAME = "quiz.exchange";
    private static final String ROUTING_KEY = "pin.*";

    // Queue 등록
    @Bean
    public Queue queue() {
        return new Queue(PIN_QUEUE_NAME, true);
    }


    // Exchange 등록
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(PIN_EXCHANGE_NAME);
    }

    // Exchange 와 Queue 바인딩
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }


    @Bean
    public  com.fasterxml.jackson.databind.Module dateTimeModule() {
        return new JavaTimeModule();
    }


    // Spring 에서 자동생성해주는 ConnectionFactory 는 SimpleConnectionFactory
    // 여기서 사용하는 건 CachingConnectionFactory 라 새로 등록해줌
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(RabbitMQ_Host);
        factory.setUsername(RabbitMQ_ID);
        factory.setPassword(RabbitMQ_PW);
        return factory;
    }
    /**
     * messageConverter를 커스터마이징 하기 위해 Bean 새로 등록
     */

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setRoutingKey(PIN_QUEUE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(PIN_QUEUE_NAME);
//        container.setMessageListener(null);
        return container;
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
