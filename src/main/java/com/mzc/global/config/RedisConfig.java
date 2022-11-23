//package com.mzc.global.config;
//
//import io.lettuce.core.RedisURI;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//    private final RedisProperties redisProperties;
//
////    @Bean
////    public RedisConnectionFactory redisConnectionFactory(){
////        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
////    }
//
//    //@Bean
//    public RedisTemplate<?, ?> redisTemplate(){
//        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        return redisTemplate;
//    }
//
//   // @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        RedisURI redisHost = RedisURI.create(redisProperties.getHost());
//        RedisURI redisPort = RedisURI.create(String.valueOf(redisProperties.getPort()));
//
//        org.springframework.data.redis.connection.RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisHost); // 제디스, 레투스 방법 으로 연결
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
//        factory.afterPropertiesSet();
//        return factory;
//    }
//
//    // 접근이 자주 되는 데이터를 캐싱하는 것이 좋음
//   //Bean
///*    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        //redisTemplate.opsForValue(); // get/set 기본적으로 할 수 있게 해주는 것
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
//        return redisTemplate;
//    }*/
//
//
//}
