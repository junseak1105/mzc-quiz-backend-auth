package com.mzc.Auth.repository;

import com.mzc.Auth.entity.HostAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostAuthRepository extends JpaRepository<HostAuth, Integer> {
    Optional<HostAuth> findByHostEmail(String hostEmail);
}
