package com.nizar.SahTech.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Long>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}
