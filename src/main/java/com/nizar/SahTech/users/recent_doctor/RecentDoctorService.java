package com.nizar.SahTech.users.recent_doctor;

import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.mobileapp.reservation.Reservation;
import com.nizar.SahTech.mobileapp.reservation.ReservationRepository;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.chat.v1.service.User;

import lombok.AllArgsConstructor;

import com.nizar.SahTech.admin.report.reportDTO;
import com.nizar.SahTech.doctor.entity.DoctorEntity;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecentDoctorService {
    private final ReservationRepository reservationRepository;
    private final DoctorRepo docRepository;

public List<DoctorEntity> getDoctor(String uid) {
            List<DoctorEntity> doctorDTOs = new ArrayList<>();

List<Reservation> reservations = reservationRepository.findByIdpatient(uid);
System.out.println(reservations);
try {
    for (Reservation reservation : reservations) {
        System.out.println(reservation.getIddoctor()   );
        DoctorEntity DoctorDTO = new DoctorEntity();

        if(reservation.getCompleted()==1){ {
            Optional<DoctorEntity> optionaldoctor=docRepository.findById(reservation.getIddoctor());
           
                DoctorDTO.setAddress(optionaldoctor.get().getAddress());
                DoctorDTO.setBio(optionaldoctor.get().getBio());
                DoctorDTO.setAddress(optionaldoctor.get().getAddress());
                DoctorDTO.setEmail(optionaldoctor.get().getEmail());
                DoctorDTO.setId(optionaldoctor.get().getId());
                DoctorDTO.setPhone(optionaldoctor.get().getPhone());
                DoctorDTO.setUsername(optionaldoctor.get().getUsername());
                DoctorDTO.setSpeciality(optionaldoctor.get().getSpeciality());
                DoctorDTO.setImage(optionaldoctor.get().getImage());
            doctorDTOs.add(DoctorDTO);


        }
        System.out.println(doctorDTOs);
    }   
 }
} catch (Exception e) {
    System.out.println(e);}
  
 return doctorDTOs;

}
} 
