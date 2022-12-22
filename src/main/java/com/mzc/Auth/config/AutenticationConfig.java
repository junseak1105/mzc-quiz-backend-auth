package com.mzc.Auth.config;


import com.mzc.Auth.service.HostAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AutenticationConfig extends WebSecurityConfigurerAdapter {

    private final HostAuthService hostAuthService;

    @Value("${jwt.secret-key}")
    private String key;

    // security 관련 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 세션 보안, 토큰을 사용한 인증방식이기 때문에 세션 보호의 필요가 없음 disable
                .authorizeRequests()
                .antMatchers("/*").permitAll()
//                .antMatchers("/*/hostauth/join", "/*/hostauth/login").permitAll() // 모든 상황에서 회원가입(해당 경로 인증 필요 없음)
//                .antMatchers("/v1/**").permitAll() // 이외의 요청은 인증이 필요
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반 사용 하지 않음
                .and()
                .exceptionHandling();
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .and()
//                .addFilterBefore(new JwtTokenFilter(hostAuthService, key), UsernamePasswordAuthenticationFilter.class);
    }
}
