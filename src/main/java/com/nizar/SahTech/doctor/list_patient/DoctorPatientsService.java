package com.nizar.SahTech.doctor.list_patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.Auth.Register.AuthService;
import com.nizar.SahTech.users.Auth.Register.SignupDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorPatientsService {
    private final DoctorPatientsRepository doctorPatientsRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final DoctorRepo doctorRepository;

public List<UserEntity>  getPatients(String id) {
                                              
    Optional  <DoctorPatients> doctorPatients = doctorPatientsRepository.findById(id); 
    if(doctorPatients.isPresent()) {
                List<UserEntity> patients = new ArrayList<>();
                UserEntity PatientsUser = new UserEntity();
                    byte[] patientIdBytes = doctorPatients.get().getPatientsList();
                    String patientIdString = new String(patientIdBytes);
                    String[]patientIdLines =patientIdString.split("\n");
                    System.out.println(patientIdLines);
                    for (String line : patientIdLines) {
                    PatientsUser = new UserEntity();
                        Optional<UserEntity> patient = userRepository.findById(line);
                        if (patient.isPresent()) {
                            PatientsUser.setImage(patient.get().getImage());
                            PatientsUser.setEmail(patient.get().getEmail());
                            PatientsUser.setPhone(patient.get().getPhone());
                            PatientsUser.setUsername(patient.get().getUsername());
                            PatientsUser.setId(patient.get().getId());
                            patients.add(PatientsUser);

                        }

                      
 }
return patients;

    }
    return null;
    }

public ResponseEntity<?> addPatient(SignupDTO userEntity , String name) {
    authService.register(userEntity).getStatusCode().value();
    Optional<DoctorEntity> doctor = doctorRepository.findByEmail(name);
if (userRepository.existsByUsername(userEntity.getEmail())) {
    Optional<UserEntity> user = userRepository.findByUsername(userEntity.getEmail());
    Optional<DoctorPatients> patient = doctorPatientsRepository.findById(doctor.get().getId());
    if (patient.get().getPatientsList() != null) {
       List<String> patientIds = new ArrayList<>();
       byte[] patientIdBytes = patient.get().getPatientsList();
       String patientIdString = new String(patientIdBytes);
       String[]patientIdLines =patientIdString.split("\n");
       for (String line : patientIdLines) {
           patientIds.add(line);
       }
       patientIds.add (user.get().getId()); ;
       StringBuilder newPatientIds = new StringBuilder();
       for (String Id : patientIds) {
           newPatientIds.append(Id).append("\n");
       }
       byte[] newPatientIdsBytes = newPatientIds.toString().getBytes();
       patient.get().setPatientsList(newPatientIdsBytes);
       patient.get().setPatientsCount(patient.get().getPatientsCount() + 1);
       doctorPatientsRepository.save(patient.get());
    
}
return ResponseEntity.ok("Patient added successfully");
    
    
}
return ResponseEntity.badRequest().body( 
    "Error"
);}
    
    
}
