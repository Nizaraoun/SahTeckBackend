package com.nizar.SahTech.doctor.repository;

import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.users.Auth.UserEntity;
@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, String>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
   Optional<DoctorEntity> findByEmail(String email);
        Optional<DoctorEntity> findByUsername(String email);
        List<DoctorEntity> findBySpeciality(String speciality);
        boolean existsByPhone(String phone);


    // default DoctorEntity findDocList() {
    //     DoctorEntity doctorEntity = new DoctorEntity();
    //     doctorEntity.setId(doctorEntity.getId());
    //     doctorEntity.setAddress(doctorEntity.getAddress());
    //     doctorEntity.setEmail(doctorEntity.getEmail());
    //     doctorEntity.setImage(doctorEntity.getImage());
    //     doctorEntity.setPhone(doctorEntity.getPhone());
    //     doctorEntity.setSpeciality(doctorEntity.getSpeciality());
        
    //     return doctorEntity;
    // }
    
}
