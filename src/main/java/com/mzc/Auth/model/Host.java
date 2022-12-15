package com.mzc.Auth.model;

import com.mzc.Auth.entity.HostAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class Host implements UserDetails {

    private Integer id;
    private String hostEmail;
    private String password;
    private String nickName;
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
                hostAuth.getNickName(),
                hostAuth.getRole(),
                hostAuth.getRegisteredAt(),
                hostAuth.getUpdatedAt(),
                hostAuth.getDeletedAt()
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getUserRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.hostEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return this.deletedAt == null;
    }
}
