package com.mzc.Auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HostLoginResponse {
    private String token;
    private String refreshToken;
}
