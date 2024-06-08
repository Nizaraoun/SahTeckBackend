package com.nizar.SahTech.doctor.list_patient;

import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.Register.SignupDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DoctorPatientsController {
    private final DoctorPatientsService doctorPatientsService;
    @PostMapping("/add-patient")
    public ResponseEntity<?> addPatient(@RequestBody SignupDTO user, Principal principal) {
        return doctorPatientsService.addPatient(
          user, 
          principal.getName()
      );

    }

    @GetMapping("/get-patients")
    public List<UserEntity> getPatients(@RequestParam String id) {
      return   doctorPatientsService.getPatients(id);


        
    }
}
