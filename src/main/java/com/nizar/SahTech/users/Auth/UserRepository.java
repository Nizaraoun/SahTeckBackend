package com.nizar.SahTech.users.Auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByUsername(String email);
    Boolean existsByUsername(String username);
    // Boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<UserEntity> findByPhone(String phone);
    Optional<UserEntity> findByEmail(String email);

    }