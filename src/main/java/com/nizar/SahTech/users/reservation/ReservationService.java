package com.nizar.SahTech.users.reservation;
import java.util.List;
import java.util.Optional;
import	java.util.ArrayList;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.repository.DoctorRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    final private ReservationRepository reservationRepository;
    final private DoctorRepo doctorRepo;
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
            reservation.setId_praticien(reservationDTO.getId_praticien());
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
    
    public ReservationDTO convertToDTO(Reservation reservation) {
            Optional<DoctorEntity> doctor = doctorRepo.findById(reservation.getId_praticien());
            ReservationDTO dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setId_patient(reservation.getIdpatient());
            dto.setId_praticien(reservation.getId_praticien());
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
     
    

        
        
    
     
}
