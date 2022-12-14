package com.mzc.Auth.service;

import com.mzc.Auth.entity.HostAuth;
import com.mzc.Auth.exception.ApplicationException;
import com.mzc.Auth.exception.ErrorCode;
import com.mzc.Auth.model.Host;
import com.mzc.Auth.repository.HostAuthRepository;
import com.mzc.Auth.util.JwtTokenUtils;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springfox.documentation.service.ResponseMessage;

@Service
@RequiredArgsConstructor
public class HostAuthService {

    private final HostAuthRepository hostAuthRepository;
    private final BCryptPasswordEncoder encoder;
//    private final UserCacheRepository redisRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMx;

    public Host loadFindByHostEmail(String hostEmail) {
        return hostAuthRepository.findByHostEmail(hostEmail).map(Host::fromEntity).orElseThrow(() ->
                new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("hostEmail is %s", hostEmail)));
    }

    public DefaultRes join(String hostEmail, String password) {
        // 회원 가입 여부 체크
        hostAuthRepository.findByHostEmail(hostEmail).ifPresent(it -> {
            throw new ApplicationException(ErrorCode.DUPLICATED_HOST_EMAIL, String.format("hostEmail is %s", hostEmail));
        });

        // 회원 가입 진행 -> host 등록
        HostAuth hostAuth = hostAuthRepository.save(HostAuth.of(hostEmail, encoder.encode(password)));
        return DefaultRes.res(StatusCode.OK, ResponseMessages.REGISTER_SUCCESS, Host.fromEntity(hostAuth));
    }

    public String login(String hostEmail, String password) {
        // 회원가입 여부 체크
        HostAuth hostAuth = hostAuthRepository.findByHostEmail(hostEmail).orElseThrow(() -> new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("not founded %s", hostEmail)));

        // 비밀 번호 체크
        //if(!userEntity.getPassword().equals(password)){
        if (!encoder.matches(password, hostAuth.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        // 토큰 생성
        String token = JwtTokenUtils.generateToken(hostEmail, secretKey, expiredTimeMx);


        return token;
    }
}
