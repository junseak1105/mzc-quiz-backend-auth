package com.mzc.Auth.controller;

import com.mzc.Auth.request.HostJoinRequest;
import com.mzc.Auth.request.HostLoginRequest;
import com.mzc.Auth.service.EmailService;
import com.mzc.Auth.service.HostAuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hostauth")
public class HostAuthController {

    final private HostAuthService hostAuthService;
    private final EmailService emailService;

    @ApiOperation(value = "호스트 회원가입", notes = "필수 데이터 : hostEmail, nickName, password")
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody HostJoinRequest request){
        return hostAuthService.join(request.getHostEmail(),request.getPassword(),request.getNickName());
    }

    @ApiOperation(value = "호스트 이메일 중복 확인", notes = "필수 데이터 : hostEmail")
    @PostMapping("/join/checkHostEmail")
    public ResponseEntity checkHostEmail(@RequestBody HostJoinRequest request){
        return hostAuthService.checkHostEmail(request.getHostEmail());
    }

    @ApiOperation(value = "호스트 회원가입 이메일 인증", notes = "필수 데이터 : hostEmail")
    @PostMapping("/join/mailConfirm")
    public ResponseEntity mailConfirm(@RequestBody HostJoinRequest request) throws Exception {
        return emailService.sendSimpleMessage(request.getHostEmail());
    }

    @ApiOperation(value = "호스트 회원가입 이메일 인증번호 확인", notes = "필수 데이터 : authNum")
    @PostMapping("/join/verifyEmail")
    public ResponseEntity verifyEmail(@RequestBody HostJoinRequest request){
        return emailService.verifyEmail(request.getAuthNum());
    }

    @ApiOperation(value = "호스트 로그인", notes = "필수 데이터 : hostEmail, password")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody HostLoginRequest request) {
        return hostAuthService.login(request.getHostEmail(), request.getPassword());

    }
}
