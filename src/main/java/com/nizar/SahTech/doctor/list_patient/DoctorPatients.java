package com.nizar.SahTech.doctor.list_patient;
import java.util.Optional;

import com.nizar.SahTech.doctor.entity.DoctorEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "doctor_patients")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatients {
    @Id
    @Column(name = "doctor_id")
    private String doctorId;
    
@Column(name = "patients_count")
    private int patientsCount;
    @Lob
    @Column(name = "patients_list", length = 20000000)
    private byte[] patientsList;
    

    



}