package com.mzc.Auth.response;

import com.mzc.Auth.model.Host;
import com.mzc.Auth.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HostResponse {
    private Integer id;
    private String hostEmail;
    private UserRole role;

    public static HostResponse fromHost(Host host){
        return new HostResponse(
                host.getId(),
                host.getHostEmail(),
                host.getUserRole()
        );
    }
}
