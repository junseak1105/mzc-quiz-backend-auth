package com.mzc.Auth.config;//package com.mzc.Auth.config;
//
//import com.mzc.Auth.service.HostAuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class AdvancedSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private HostAuthService hostAuthService; // user-admin-service-SpUserService 내용
//
//    @Bean
////    PasswordEncoder passwordEncoder() {
////        return NoOpPasswordEncoder.getInstance();
////    }
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // LoginFilter의 경우 UserAuthenticationManager가 있기에 매니저에게 검증을 위임하면 되지만
//        // CheckFilter는 사용자를 직접 가져올 상황이 생김
//
//        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), hostAuthService); // 로그인 처리
//        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), hostAuthService); // 로그인시 가져오는 토큰 검사
//
//        http.csrf().disable()
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )// 세션 사용 하지 않게 설정
//                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(checkFilter, BasicAuthenticationFilter.class);
//    }
//}
