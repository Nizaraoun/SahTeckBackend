package com.nizar.SahTech.role.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.role.dto.Role;

@Repository

public interface RoleRep extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
