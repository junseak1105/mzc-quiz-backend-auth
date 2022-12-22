package com.mzc.Auth.service;

import com.mzc.Auth.entity.Host;
import com.mzc.Auth.entity.HostAuth;
import com.mzc.Auth.repository.HostAuthRepository;
import com.mzc.Auth.util.JwtTokenUtils;
import com.mzc.global.Response.DefaultRes;
import com.mzc.global.Response.ResponseMessages;
import com.mzc.global.Response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HostAuthService implements UserDetailsService{
    //    public class HostAuthService implements UserDetailsService {
    private final HostAuthRepository hostAuthRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMx;

//    public Host loadFindByHostEmail(String hostEmail) {
//        return hostAuthRepository.findByHostEmail(hostEmail).map(Host::fromEntity).orElseThrow(() ->
//                new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("hostEmail is %s", hostEmail)));
//    }

    @Override
    public UserDetails loadUserByUsername(String hostEmail) throws UsernameNotFoundException {
        return (UserDetails) hostAuthRepository.findByHostEmail(hostEmail).orElseThrow(
                ()->new UsernameNotFoundException(hostEmail));
    }

    public ResponseEntity join(String hostEmail, String password, String nickName) {
        // 회원 가입 여부 체크
        Optional<HostAuth> hostAuth = hostAuthRepository.findByHostEmail(hostEmail);
//        if(hostAuth.isPresent()){
//            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.DUPLICATED_HOST_EMAIL), HttpStatus.BAD_REQUEST);
//        }

        // 회원 가입 진행 -> host 등록
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.REGISTER_SUCCESS, Host.fromEntity(hostAuthRepository.save(HostAuth.of(hostEmail, encoder.encode(password),nickName)))), HttpStatus.OK);
    }

    public ResponseEntity checkHostEmail(String hostEmail) {
        Optional<HostAuth> hostAuth = hostAuthRepository.findByHostEmail(hostEmail);
        if(hostAuth.isPresent()){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.DUPLICATED_HOST_EMAIL), HttpStatus.OK);
        }
        // 회원 가입 진행 -> host 등록
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.HOST_EMAIL_CHECK_OK), HttpStatus.OK);
    }

    public ResponseEntity login(String hostEmail, String password) {

        Optional<HostAuth> hostAuth = hostAuthRepository.findByHostEmail(hostEmail);

        if(!hostAuth.isPresent()){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.HOST_EMAIL_NOT_FOUND), HttpStatus.OK);
        }else{
            //return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.Login_SUCCESS), HttpStatus.OK);
            //DefaultRes.res(StatusCode.OK, ResponseMessages.Login_SUCCESS);
        }

        // 비밀 번호 체크
        if (!encoder.matches(password, hostAuth.get().getPassword())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessages.INVALID_PASSWORD), HttpStatus.OK);
        }
        // 토큰 생성
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessages.CREATE_TOKEN, JwtTokenUtils.generateToken(hostEmail, secretKey, expiredTimeMx)), HttpStatus.OK);
    }
}
