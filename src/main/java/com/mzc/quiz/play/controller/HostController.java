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

// JWT를 통해서 SSO 로그인 인증
// username을 -> MongoDB 조회
// Redis 저장공간 생성
// 1. 유저리스트 저장
// 2. 문제별 정보 저장 -> Hash로 해도 될려나
// 3.
