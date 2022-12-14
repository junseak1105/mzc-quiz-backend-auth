package com.mzc.Auth.controller;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.request.HostJoinRequest;
import com.mzc.Auth.request.HostLoginRequest;
import com.mzc.Auth.response.HostJoinReponse;
import com.mzc.Auth.response.HostLoginResponse;
import com.mzc.Auth.response.HostResponse;
import com.mzc.Auth.response.Response;
import com.mzc.Auth.service.HostAuthService;
import com.mzc.global.Response.DefaultRes;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hostauth")
public class HostAuthController {

    final private HostAuthService hostAuthService;

//    @PostMapping("/join")
//    public Response<HostJoinReponse> join(@RequestBody HostJoinRequest request){
//        Host host = hostAuthService.join(request.getHostEmail(),request.getPassword());
//        System.out.println("Email:"+request.getHostEmail());
//        System.out.println("PW:"+request.getPassword());
//        return Response.success(HostJoinReponse.fromHost(host));
//    }

    @ApiOperation(value = "호스트 로그인", notes = "호스트 로그인")
    @PostMapping("/join")
    public DefaultRes join(@RequestBody HostJoinRequest request){
        return hostAuthService.join(request.getHostEmail(),request.getPassword());
    }

    @PostMapping("/login")
    public Response<HostLoginResponse> login(@RequestBody HostLoginRequest request) {
        String token = hostAuthService.login(request.getHostEmail(), request.getPassword());
        return Response.success(new HostLoginResponse(token));
    }

}
