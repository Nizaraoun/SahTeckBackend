package com.nizar.SahTech.doctor.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Long>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

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
