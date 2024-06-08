package com.nizar.SahTech.doctor.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.entity.DoctorEntity.Plan;
import com.nizar.SahTech.doctor.repository.DoctorRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class subscriptionService {
private final DoctorRepo doctorRepo;

public String addSubscription(String id, Plan plan) {
    Optional<DoctorEntity> doctor = doctorRepo.findById(id);
    if(doctor.isPresent()) {
        doctor.get().setSubscription(plan);
        doctorRepo.save(doctor.get());
        return "Subscription added successfully";
    }
    return "No subscription found";}

public String getSubscription(String uid) {

    Optional<DoctorEntity> doctor = doctorRepo.findById(uid);
    if(doctor.isPresent()) {
        return doctor.get().getSubscription().toString();

    }
    return "No subscription found";
}
    
}
