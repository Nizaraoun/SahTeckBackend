package com.nizar.SahTech.users.medical_doc;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
@Repository
public interface FilesRepository extends JpaRepository<MedicalFile, String> {

    Optional<MedicalFile> findByUserId(String id);


} 