package com.nizar.SahTech.mobileapp.reservation;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.twilio.twiml.voice.Connect;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class ReservationController {
    final private ReservationService reservationService;
    final private DoctorRepo doctorRepo;
    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestBody ReservationDTO reservationDTO , Principal connecteduser) {
        try {
            if (reservationDTO == null || reservationDTO.getJour() == null || reservationDTO.getJour().length() == 0) {
                return ResponseEntity.badRequest().body("Jour is required");
            }
        
            else{
              if (connecteduser.getName() != null  ) {
                reservationDTO.setPatientemail(connecteduser.getName());
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

    //Get all reservations for the web dashboard
    @GetMapping("/getAllReservationsForDashboard")
    public List<ReservationDTO> getAllReservationsForDashboard(Principal connectedDoctor) {
        Optional<DoctorEntity> doctor = doctorRepo.findByUsername(connectedDoctor.getName());
        return reservationService.getAllReservationsForDashboard(
            doctor.get().getId()
        );
    }


    
//completed reservation 
    @PostMapping("/completeReservation")
    public ResponseEntity<?> completeTheReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.completeTheReservation(reservationDTO.getId());
    }
    // update reservation
    @PostMapping("/updateReservation")
    public ResponseEntity<?> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.updateReservation(reservationDTO);
    }
}
