package com.mzc.Auth.config;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzc.Auth.entity.Host;
import com.mzc.Auth.service.HostAuthService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    // 유효한 사용자에게 인증 필터를 주는 곳
    private ObjectMapper objectMapper = new ObjectMapper();

    private HostAuthService hostAuthService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, HostAuthService hostAuthService){
        super(authenticationManager);
        this.hostAuthService = hostAuthService;
        setFilterProcessesUrl("/login"); // post url mapping 처리
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        UserLoginForm userLogin = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);
        if(userLogin.getRefreshToken() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLogin.getUsername(), userLogin.getPassword(), null
            );
            // user details 처리 부분
            return getAuthenticationManager().authenticate(token); // authentication manager에게 token 값 검증을 위해 전달
        }else{
            VerifyResult verify = JWTUtil.verify(userLogin.getRefreshToken());
            if(verify.isSuccess()){
                Host host = (Host)hostAuthService.loadUserByUsername(verify.getUsername()); // 유저 정보 가져 오는 곳
                //UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                return new UsernamePasswordAuthenticationToken(
                        host, host.getAuthorities()
                );
            }else {
                throw new TokenExpiredException("refresh token expired");
            }
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException
    {
        Host host = (Host) authResult.getPrincipal();

        // login이 완료되려면 토큰을 발행해 줘야 함
        //response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + JWTUtil.makeAuthToken(user)); // JWTUtil에서 생성된 토큰값 header에 저장
        response.setHeader("auth_token", JWTUtil.makeAuthToken(host));
        response.setHeader("refresh_token", JWTUtil.makeRefreshToken(host));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // json 값을 받기 위함
        response.getOutputStream().write(objectMapper.writeValueAsBytes(host));
    }
}
