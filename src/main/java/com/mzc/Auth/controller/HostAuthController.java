package com.mzc.Auth.controller;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.request.HostJoinRequest;
import com.mzc.Auth.response.HostJoinReponse;
import com.mzc.Auth.response.HostResponse;
import com.mzc.Auth.response.Response;
import com.mzc.Auth.service.HostAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hostauth")
public class HostAuthController {

    final private HostAuthService hostAuthService;

    @PostMapping("/join")
    public Response<HostJoinReponse> join(@RequestBody HostJoinRequest request){
        Host host = hostAuthService.join(request.getHostEmail(),request.getPassword());
        return Response.success(HostJoinReponse.fromHost(host));
    }



}
