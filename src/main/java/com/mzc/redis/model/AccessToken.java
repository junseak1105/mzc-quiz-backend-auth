package com.mzc.redis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("AccessToken")
public class AccessToken implements Serializable {
    @Id
    private String id;
    private String token;
    private int expired;

    @Builder
    public AccessToken(String id, String token, int expired){
        this.id = id;
        this.token = token;
        this.expired = expired;
    }
}
