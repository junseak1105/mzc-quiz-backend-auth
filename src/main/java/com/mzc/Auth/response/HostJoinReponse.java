package com.mzc.Auth.response;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HostJoinReponse {
    private Integer id;
    private String hostEmail;
    private String nickName;
    private UserRole role;

    public static HostJoinReponse fromHost(Host host){
        return new HostJoinReponse(
          host.getId(),
          host.getHostEmail(),
          host.getNickName(),
          host.getUserRole()
        );
    }
}
