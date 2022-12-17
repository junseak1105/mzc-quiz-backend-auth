package com.mzc.Auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostJoinRequest {
    private String hostEmail;
    private String password;
    private String nickName;
    private String authNum;
}
