package com.mzc.Auth.service;

import com.mzc.Auth.entity.HostAuth;
import com.mzc.Auth.exception.ApplicationException;
import com.mzc.Auth.exception.ErrorCode;
import com.mzc.Auth.model.Host;
import com.mzc.Auth.repository.HostAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostAuthService {

    private final HostAuthRepository hostAuthRepository;
    private final BCryptPasswordEncoder encoder;
//    private final UserCacheRepository redisRepository;

    public Host loadFindByHostEmail(String hostEmail) {
        return hostAuthRepository.findByHostEmail(hostEmail).map(Host::fromEntity).orElseThrow(() ->
                new ApplicationException(ErrorCode.HOST_EMAIL_NOT_FOUND, String.format("hostEmail is %s", hostEmail)));
    }

    public Host join(String hostEmail, String password) {
        // 회원 가입 여부 체크
        hostAuthRepository.findByHostEmail(hostEmail).ifPresent(it -> {
            throw new ApplicationException(ErrorCode.DUPLICATED_HOST_EMAIL, String.format("hostEmail is %s", hostEmail));
        });

        // 회원 가입 진행 -> host 등록
        HostAuth hostAuth = hostAuthRepository.save(HostAuth.of(hostEmail, encoder.encode(password)));
        return Host.fromEntity(hostAuth);
    }
}
