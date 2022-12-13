package com.mzc.Auth.model;

import com.mzc.Auth.entity.HostAuth;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class Host{

    private Integer id;
    private String hostEmail;
    private String password;
    private UserRole userRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    // entity를 dto로 변경 해주는 부분
    public static Host fromEntity(HostAuth hostAuth) {
        return new Host(
                hostAuth.getId(),
                hostAuth.getHostEmail(),
                hostAuth.getPassword(),
                hostAuth.getRole(),
                hostAuth.getRegisteredAt(),
                hostAuth.getUpdatedAt(),
                hostAuth.getDeletedAt()
        );
    }
}
