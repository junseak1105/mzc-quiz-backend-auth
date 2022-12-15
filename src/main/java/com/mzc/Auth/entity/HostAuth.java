package com.mzc.Auth.entity;

import com.mzc.Auth.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "\"hostauth\"") // 생성 테이블 명
@SQLDelete(sql = "UPDATE \"hostauth\" SET deletedAt = NOW() WHERE id=?") // 삭제시 실행
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class HostAuth{
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스 사용
        private Integer id = null;

        @Column(name = "hostEmail")
        private String hostEmail;

        @Column(name = "password")
        private String password;

        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        private UserRole role = UserRole.HOST; // 권한 db에 저장

        @Column(name = "registered_at")
        private Timestamp registeredAt; // 등록 시간

        @Column(name = "updated_at")
        private Timestamp updatedAt; // 업데이트 시간

        @Column(name = "deleted_at")
        private Timestamp deletedAt; // 삭제 시간

        @PrePersist
        void registeredAt() { // 등록 전 시간 저장
            this.registeredAt = Timestamp.from(Instant.now());
        }

        @PreUpdate
        void updatedAt() { // 업데이트 전 시간 저장
            this.updatedAt = Timestamp.from(Instant.now());
        }

        // UserEntity 반환
        public static HostAuth of(String setHostEmail, String password) {
            HostAuth hostAuth = new HostAuth();
            hostAuth.setHostEmail(setHostEmail);
            hostAuth.setPassword(password);
            return hostAuth;
        }
    }
