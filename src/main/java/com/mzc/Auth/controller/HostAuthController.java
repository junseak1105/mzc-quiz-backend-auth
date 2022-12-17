package com.mzc.Auth.controller;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.request.HostJoinRequest;
import com.mzc.Auth.request.HostLoginRequest;
import com.mzc.Auth.response.HostJoinReponse;
import com.mzc.Auth.response.HostLoginResponse;
import com.mzc.Auth.response.HostResponse;
import com.mzc.Auth.response.Response;
import com.mzc.Auth.service.EmailService;
import com.mzc.Auth.service.HostAuthService;
import com.mzc.global.Response.DefaultRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hostauth")
public class HostAuthController {

    final private HostAuthService hostAuthService;
    private final EmailService emailService;

//    @PostMapping("/join")
//    public Response<HostJoinReponse> join(@RequestBody HostJoinRequest request){
//        Host host = hostAuthService.join(request.getHostEmail(),request.getPassword());
//        return Response.success(HostJoinReponse.fromHost(host));
//    }

    //@ApiOperation(value = "호스트 로그인", notes = "필수 데이터 : hostEmail, password")
//    @PostMapping("/login")
//    public Response<HostLoginResponse> login(@RequestBody HostLoginRequest request) {
//        String token = hostAuthService.login(request.getHostEmail(), request.getPassword());
//        return Response.success(new HostLoginResponse(token));
//    }

    @ApiOperation(value = "호스트 회원가입", notes = "필수 데이터 : hostEmail, nickName, password")
    @PostMapping("/join")
    public DefaultRes join(@RequestBody HostJoinRequest request){
        return hostAuthService.join(request.getHostEmail(),request.getPassword(),request.getNickName());
    }

    @ApiOperation(value = "호스트 이메일 중복 확인", notes = "필수 데이터 : hostEmail")
    @PostMapping("/join/checkHostEmail")
    public DefaultRes checkHostEmail(@RequestBody HostJoinRequest request){
        return hostAuthService.checkHostEmail(request.getHostEmail());
    }

//    @PostMapping("/join/mailConfirm")
//    @ResponseBody
//    public String mailConfirm(@RequestBody HostJoinRequest request) throws Exception {
//        String code = emailService.sendSimpleMessage(request.getHostEmail());
//        log.info("인증코드 : " + code);
//        return code;
//    }
    @ApiOperation(value = "호스트 회원가입 이메일 인증", notes = "필수 데이터 : hostEmail")
    @PostMapping("/join/mailConfirm")
    public DefaultRes mailConfirm(@RequestBody HostJoinRequest request) throws Exception {
//        String code = emailService.sendSimpleMessage(email);
//        log.info("인증코드 : " + code);
        return emailService.sendSimpleMessage(request.getHostEmail());
    }

    @ApiOperation(value = "호스트 회원가입 이메일 인증번호 확인", notes = "필수 데이터 : authNum")
    @PostMapping("/join/verifyEmail")
    public DefaultRes verifyEmail(@RequestBody HostJoinRequest request){
        return emailService.verifyEmail(request.getAuthNum());
    }

    @ApiOperation(value = "호스트 로그인", notes = "필수 데이터 : hostEmail, password")
    @PostMapping("/login")
    public DefaultRes login(@RequestBody HostLoginRequest request) {
        return hostAuthService.login(request.getHostEmail(), request.getPassword());

    }

//    @GetMapping("/host")
//    public DefaultRes host(Authentication authentication) {
//        return hostAuthService.loadFindByHostEmail(authentication.getName());
//    }
}
