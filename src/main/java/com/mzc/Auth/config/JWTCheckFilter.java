package com.mzc.Auth.config;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mzc.Auth.entity.Host;
import com.mzc.Auth.service.HostAuthService;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTCheckFilter extends BasicAuthenticationFilter {
    private HostAuthService hostAuthService;

    // 토큰을 검사해서 security context holder에 user principal 정보를 채워 주는 역할
    public JWTCheckFilter(AuthenticationManager authenticationManager, HostAuthService hostAuthService) {
        super(authenticationManager);
        this.hostAuthService = hostAuthService;
    }

    // 이를 통해 토큰을 통한 검사
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearer == null || !bearer.startsWith("Bearer ")){ // header 검사
            chain.doFilter(request, response); // 인증이 필요하지 않은 경우
            return;
        }

        String token = bearer.substring("Bearer ". length()); // header에서 bearer의 토큰 값을 가져 오는 곳
        VerifyResult result = JWTUtil.verify(token);
        if(result.isSuccess()){ // 인증된 사용자인 경우
            Host host = (Host)hostAuthService.loadUserByUsername(result.getUsername()); // 유저 정보 가져 오는 곳
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    host.getUsername(), null, host.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request, response);
        } else{
            throw new TokenExpiredException("Token is not valid");
        }
    }
}
