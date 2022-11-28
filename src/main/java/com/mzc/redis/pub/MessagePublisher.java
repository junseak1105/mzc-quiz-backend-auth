package com.mzc.redis.pub;

public interface MessagePublisher {
    void publish(String message);
}