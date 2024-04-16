package com.nizar.SahTech.users.reservation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    // get all reservations for a doctor

    public List<String> getReservedHoursForDoctorOnDay(String idPraticien, String jour) {
        return reservationRepository.findReservedHoursForDoctorOnDay(idPraticien, jour);
    }

        
        
    
     
}
