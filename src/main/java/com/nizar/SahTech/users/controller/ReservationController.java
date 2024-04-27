package com.nizar.SahTech.users.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.users.reservation.Reservation;
import com.nizar.SahTech.users.reservation.ReservationDTO;
import com.nizar.SahTech.users.reservation.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ReservationController {
    final private ReservationService reservationService;
    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDTO reservationDTO , Principal connecteduser) {
        try {
            if (reservationDTO == null || reservationDTO.getJour() == null || reservationDTO.getJour().length() == 0) {
                return ResponseEntity.badRequest().body("Jour is required");
            }
        
            else{
              if (connecteduser.getName() != null  ) {
                reservationDTO.setUsername(connecteduser.getName());
                return reservationService.addReservation(reservationDTO);
            }
            else{
                return ResponseEntity.badRequest().body("username is required");
            }
        }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add reservation: " + e.getMessage());
        }
    }
    @GetMapping("/getDoctorResrvation")

    public List<String> getReservedHoursForDoctorOnDay(@RequestParam("id_praticien") String idPraticien, @RequestParam("jour") String jour) {
        return reservationService.getReservedHoursForDoctorOnDay(idPraticien, jour);

    }

    // Get Upcoming Reservations
    @GetMapping("/getUpcomingReservations")
    public List<ReservationDTO> getUpcomingReservations(@RequestParam String id ){
        return reservationService.getUserReservations(id);
    }
    // Cancel Reservation
    @PostMapping("/CancelReservation")
    public ResponseEntity<String> deleteReservation(@RequestParam Long id) {
        return reservationService.deleteReservation(id);
    }



    


    

}
