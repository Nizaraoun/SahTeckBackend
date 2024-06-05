package com.nizar.SahTech.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.mobileapp.feed.FeedRepository;
import com.nizar.SahTech.users.Auth.Otp.OtpRequest;
import com.nizar.SahTech.users.Auth.Otp.SmsService;

import lombok.AllArgsConstructor;

@Service
 @AllArgsConstructor
public class webService {

        private final DoctorRepo doctorRepository;
        private final FeedRepository feedRepository;
        private final SmsService smsService;

        public  List<DoctorEntity> getAllDoctors() {
            return doctorRepository.findAll();
        }

        public DoctorEntity getDoctorById(String id) {
            return doctorRepository.findById(id).orElse(null);
        }

        public DoctorEntity getDoctorByUsername(String username) {
            return doctorRepository.findByEmail(username).orElse(null);
        }
        public DoctorEntity approuverDoctor ( String id) {
            DoctorEntity doctor = doctorRepository.findById(id).orElse(null);
            if (doctor != null) {
                doctor.setIsActive(true);
                smsService.SendMail(new OtpRequest(doctor.getUsername(),  doctor.getEmail(), "Your account has been approved Welcome to Sah'tech", null));
                doctor.setPassword(null);
                doctorRepository.save(doctor);
            }
            return doctor;
        }
        public String desapprouverDoctor (String id) {
            doctorRepository.deleteById(id);
            return "Doctor desapprouved";
        }
        public String deleteDoctor(String id) {
            doctorRepository.deleteById(id);
            return "Doctor deleted";
        }

          public String deletePub(Long id) {
            feedRepository.deleteById(id);
            return "Feed deleted";
        }

        public List<DoctorEntity> getNewDoctor() {

            return doctorRepository.findByIsActive(false);

        }


}
