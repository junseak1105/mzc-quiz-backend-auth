package com.mzc.Auth.repository;

import com.mzc.Auth.entity.HostAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HostAuthRepository extends JpaRepository<HostAuth, Integer> {
    //Optional<HostAuth> findByHostEmail(String hostEmail);
    Optional<HostAuth> findByHostEmail(String hostEmail);

}
