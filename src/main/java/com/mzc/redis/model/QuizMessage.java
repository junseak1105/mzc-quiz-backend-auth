package com.mzc.redis.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@RedisHash(value = "pin_num")
public class QuizMessage {
    @Id
    private List pin_num;
    private int client_count;
    private List<QuizPlayEntity> client_list;

}
