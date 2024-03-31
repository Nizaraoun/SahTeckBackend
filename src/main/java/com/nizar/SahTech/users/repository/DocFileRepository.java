package com.nizar.SahTech.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nizar.SahTech.users.entite.MedicalFile;

public interface DocFileRepository extends JpaRepository<MedicalFile, Long> {

} 