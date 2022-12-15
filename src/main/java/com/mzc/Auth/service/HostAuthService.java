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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostAuthService {

    private final HostAuthRepository hostAuthRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMx;

    public Host loadFindByHostEmail(String hostEmail) {
        return hostAuthRepository.findByHostEmail(hostEmail).map(Host::fromEntity).orElseThrow(() ->
                new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("hostEmail is %s", hostEmail)));
    }

    public DefaultRes join(String hostEmail, String password, String nickName) {
        // 회원 가입 여부 체크
//        hostAuthRepository.findByHostEmail(hostEmail).ifPresent(it -> {
//            throw new ApplicationException(ErrorCode.DUPLICATED_HOST_EMAIL, String.format("hostEmail is %s", hostEmail));
//        });

        Optional<HostAuth> hostAuth = hostAuthRepository.findByHostEmail(hostEmail);
        if(hostAuth.isPresent()){
            return DefaultRes.res(StatusCode.BAD_REQUEST,ResponseMessages.DUPLICATED_HOST_EMAIL);
        }

        // 회원 가입 진행 -> host 등록
        return DefaultRes.res(StatusCode.OK, ResponseMessages.REGISTER_SUCCESS, Host.fromEntity(hostAuthRepository.save(HostAuth.of(hostEmail, encoder.encode(password),nickName))));
    }

    public DefaultRes login(String hostEmail, String password) {

        Optional<HostAuth> hostAuth = hostAuthRepository.findByHostEmail(hostEmail);
        if(hostAuth.isPresent()){
            DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.HOST_EMAIL_NOT_FOUND);
        }else{
            DefaultRes.res(StatusCode.OK, ResponseMessages.Login_SUCCESS);
        }

        // 회원가입 여부 체크
        //HostAuth hostAuth = hostAuthRepository.findByHostEmail(hostEmail).orElseThrow(() -> new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("not founded %s", hostEmail)));

        // 비밀 번호 체크
        //if(!userEntity.getPassword().equals(password)){
        if (!encoder.matches(password, hostAuth.get().getPassword())) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.INVALID_PASSWORD);
//            throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        // 토큰 생성
        return DefaultRes.res(StatusCode.OK, ResponseMessages.CREATE_TOKEN, JwtTokenUtils.generateToken(hostEmail, secretKey, expiredTimeMx));
    }
}
