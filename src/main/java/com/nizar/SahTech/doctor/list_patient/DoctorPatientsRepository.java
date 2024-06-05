package com.nizar.SahTech.doctor.list_patient;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorPatientsRepository extends JpaRepository<DoctorPatients, String>{
    Optional<DoctorPatients> findByDoctorId(String id);

}