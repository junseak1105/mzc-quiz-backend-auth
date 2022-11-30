package com.mzc.quiz.play.controller;

import com.mzc.global.Response.DefaultRes;
import com.mzc.quiz.play.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HostController {

    @Autowired
    private HostService hostService;

    @PostMapping("/v1/host/createPlay")
    public DefaultRes createPlay(){
        return hostService.createPlay();
    }
    
}
