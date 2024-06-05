package com.nizar.SahTech.doctor.service;

import java.sql.Date;
import java.util.Optional;
import java.util.Collections;

import javax.print.Doc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.dto.SignUpDto;
import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.list_patient.DoctorPatients;
import com.nizar.SahTech.doctor.list_patient.DoctorPatientsRepository;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.role.dto.Role;
import com.nizar.SahTech.role.repository.RoleRep;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.util.IdGenerator;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DoctorService {
 private final    DoctorRepo doctorRepo;
     private final DoctorRepo docRepository;
    private final RoleRep roleRepository;
    private final PasswordEncoder passwordEncoder;
        private final DoctorPatientsRepository doctorPatientsRepository;

    
    public String getDoctor(String uid) {
        DoctorEntity doctor = new DoctorEntity();
       Optional<DoctorEntity> optionaldoctor=doctorRepo.findById(uid);
         if(optionaldoctor.isPresent()) {
              doctor=optionaldoctor.get();
         }
        return doctor.getUsername();
    }


    public ResponseEntity <String> registerDoctor(SignUpDto signupDTO) {
        if (docRepository.existsByPhone(signupDTO.getPhone())) {
            return new ResponseEntity<>("Phone is taken!", HttpStatus.BAD_REQUEST);
        }

        // Check if the email is already registered
        if (docRepository.existsByEmail(signupDTO.getEmail())) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime createdAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedCreatedAt = createdAt.format(formatter);

        DoctorPatients doctorPatients = new DoctorPatients();
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(IdGenerator.generateId(24));
        doctor.setUsername(signupDTO.getUsername());
        doctor.setPhone(signupDTO.getPhone());
        doctor.setEmail(signupDTO.getEmail());
        doctor.setSpeciality(signupDTO.getSpecialty());
        doctor.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        doctor.setIsActive(false);
        doctor.setCreationDate(formattedCreatedAt);
        doctorPatients.setDoctorId(doctor.getId());
        doctorPatients.setPatientsList("".getBytes());
        doctor.setBio("");
        doctor.setFollowers(0);
        doctor.setRating(0.0);
        doctor.setSubscription(DoctorEntity.Plan.FREE);

        Optional<Role> doctorRoleOptional = roleRepository.findByName("DOCTOR");
        if (doctorRoleOptional.isEmpty()) {
            return new ResponseEntity<>("Error during registration. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
        Role doctorRole = doctorRoleOptional.get();
        doctor.setRoles(Collections.singletonList(doctorRole));
        doctorPatientsRepository.save(doctorPatients);

        docRepository.save(doctor);
        return new ResponseEntity<>("Doctor registered successfully!", HttpStatus.OK);
    }



}
