package com.nizar.SahTech.doctor.list_patient;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.users.Auth.UserEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DoctorPatientsController {
    private final DoctorPatientsService doctorPatientsService;
    @PostMapping("/add-patient")
    public void addPatient() {
    }

    @GetMapping("/get-patients")
    public List<UserEntity> getPatients(@RequestParam String id) {
      return   doctorPatientsService.getPatients(id);


        
    }
}
