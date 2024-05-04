package com.nizar.SahTech.mobileapp.reservation;
import java.util.List;
import java.util.Optional;
import	java.util.ArrayList;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.chat.v1.service.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    final private ReservationRepository reservationRepository;
    final private DoctorRepo doctorRepo;
    final private UserRepository userRepository;
    //add reservation
 public   ResponseEntity<String> addReservation(ReservationDTO reservationDTO) {
    Optional<DoctorEntity> doctor = doctorRepo.findById(reservationDTO.getId_praticien());
    
        Reservation reservation = new Reservation();
        try {
            reservation.setJour(reservationDTO.getJour());
            reservation.setHeure(reservationDTO.getHeure());
            reservation.setUsername(reservationDTO.getUsername());
            reservation.setDoctorname(doctor.get().getUsername());
            reservation.setIdpatient(reservationDTO.getId_patient());
            reservation.setIddoctor(reservationDTO.getId_praticien());
            reservation.setCancel(false);
            reservation.setCompleted(0);
            reservation.setSpecialty(doctor.get().getSpeciality());
            reservationRepository.save(reservation);

            return ResponseEntity.ok("Reserv--ation added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add reservation: " + e.getMessage());
        }
    }

    //delete reservation
  public  ResponseEntity<String> deleteReservation(Long id) {
    Optional<Reservation> reservation = reservationRepository.findById(id);
        try {
            if (reservation.isPresent()) {
                reservation.get().setCancel(true);
                reservationRepository.save(reservation.get());
                return ResponseEntity.ok("Reservation canceled successfully");
            }
            return ResponseEntity.ok("Reservation not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to cancel the reservation: " + e.getMessage());
        }
    }

    // get all reservations for a doctor

    public List<String> getReservedHoursForDoctorOnDay(String idPraticien, String jour) {
        return reservationRepository.findReservedHoursForDoctorOnDay(idPraticien, jour);
    }
// get all upcoming reservations for a user
    public List<ReservationDTO> getUserReservations(String id) {
        List<ReservationDTO> reservations = new ArrayList<>();
        Optional<List<Reservation>> reservationsOptional = reservationRepository.findReservationsByIdpatient(id);

        try {
            
            reservationsOptional.ifPresent(reservationList -> {
                for (Reservation reservation : reservationList) {
                    ReservationDTO reservationDTO = convertToDTO(reservation);
                    reservations.add(reservationDTO);
                }
            });
            
        } catch (Exception e) {
            // Handle exception
        }
        return reservations;
    }
    

// get all reservations for the web dashboard
    public List<ReservationDTO> getAllReservationsForDashboard(String id) {
        List<ReservationDTO> reservations = new ArrayList<>();
        Optional<List<Reservation>> reservationsOptional = reservationRepository.findByIddoctor(id);


        try {
            
            reservationsOptional.ifPresent(reservationList -> {

                for (Reservation reservation : reservationList) {
                    ReservationDTO reservationDTO = reservationList(reservation);
                    reservations.add(reservationDTO);
                }
            });
            
        } catch (Exception e) {
            // Handle exception
        }
        return reservations;
    
    }


    //function to convert reservation to DTO
    public ReservationDTO convertToDTO(Reservation reservation) {
        Optional<DoctorEntity> doctor = doctorRepo.findById(reservation.getIddoctor());
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setId_patient(reservation.getIdpatient());
        dto.setId_praticien(reservation.getIddoctor());
        dto.setJour(reservation.getJour());
        dto.setHeure(reservation.getHeure());
        dto.setUsername(reservation.getUsername());
        dto.setDoctorname(reservation.getDoctorname());
        dto.setSpecialty(reservation.getSpecialty());
        dto.setImage(doctor.get().getImage());
        dto.setCancel(reservation.isCancel());
        dto.setCompleted(reservation.getCompleted());

   
        return dto;
    }
    public ReservationDTO reservationList(Reservation reservation ) {
        Optional <UserEntity> user = userRepository.findById(reservation.getIdpatient());

        ReservationDTO dto = new ReservationDTO();
    
        if (reservation.getCompleted() == 0 || reservation.isCancel()  == false) {
            dto.setId(reservation.getId());
            dto.setPatientname(user.get().getEmail());
            dto.setId_patient(reservation.getIdpatient());
            dto.setJour(reservation.getJour());
            dto.setHeure(reservation.getHeure()); 
            dto.setPatientphone(user.get().getPhone());
        }
        return dto;
    }

        
        
    
     
}