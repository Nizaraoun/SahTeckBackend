package com.nizar.SahTech.users.service;

import java.security.Principal;
import java.util.Optional;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.dto.ReservationDTO;
import com.nizar.SahTech.users.entite.Reservation;
import com.nizar.SahTech.users.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

    final private ReservationRepository reservationRepository;

    //add reservation
 public   ResponseEntity<String> addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        try {
            reservation.setJour(reservationDTO.getJour());
            reservation.setHeure(reservationDTO.getHeure());
            reservation.setUsername(reservationDTO.getUsername());
            reservation.setDoctorname(reservationDTO.getDoctorname());
            reservation.setId_patient(reservationDTO.getId_patient());
            reservation.setId_praticien(reservationDTO.getId_praticien());
            reservationRepository.save(reservation);
            return ResponseEntity.ok("Reservation added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add reservation: " + e.getMessage());
        }
    }

    //delete reservation
  public  ResponseEntity<String> deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete reservation: " + e.getMessage());
        }
    }

    public List<String> getReservedHoursForDoctorOnDay(Long idPraticien, String jour) {
        return reservationRepository.findReservedHoursForDoctorOnDay(idPraticien, jour);
    }

        
        
    
     
}
