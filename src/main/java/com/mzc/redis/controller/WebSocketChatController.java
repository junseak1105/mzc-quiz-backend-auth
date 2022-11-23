package com.mzc.redis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class WebSocketChatController {

    @GetMapping("/chat")
    public String chatGET(){
        log.info("@ChatController, chat GET()");

        return "chat";
    }
}
