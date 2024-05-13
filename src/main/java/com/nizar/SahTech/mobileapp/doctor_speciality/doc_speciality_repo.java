package com.nizar.SahTech.mobileapp.doctor_speciality;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nizar.SahTech.doctor.entity.DoctorEntity;

/**
 * doc_speciality_repo
 */
public interface doc_speciality_repo extends JpaRepository<DoctorEntity, String>{

    
} 