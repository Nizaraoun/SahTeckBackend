package com.nizar.SahTech.doctor.list_patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorPatientsService {
    private final DoctorPatientsRepository doctorPatientsRepository;
    private final UserRepository userRepository;

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
    
    
}
