package com.mzc.Auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HostLoginRequest {
    private String hostEmail;
    private String password;

    private String refreshToken;
}
