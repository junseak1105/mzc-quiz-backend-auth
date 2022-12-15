package com.mzc.Auth.exception;

import com.mzc.Auth.response.Response;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(ErrorCode.INVALID_TOKEN.getStatus().value()); // 토큰 관련 상태 메시지
        response.getWriter().write(Response.error(ErrorCode.INVALID_TOKEN.name()).toStream());
    }
}
