package com.nizar.SahTech.doctor.dto;
import lombok.Data;
import java.util.Optional;

import com.nizar.SahTech.doctor.entity.DoctorEntity;

@Data
public class AuthDrResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private Optional <DoctorEntity> doctor;
    private int patientsCount;


    public AuthDrResponseDTO(String accessToken , Optional<DoctorEntity> doctor2 , int patientsCount) {
        this.accessToken = accessToken;
        this.doctor = doctor2;
        this.patientsCount = patientsCount;

    }   




}
