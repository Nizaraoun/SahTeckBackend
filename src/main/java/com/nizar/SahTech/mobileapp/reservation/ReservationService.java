package com.nizar.SahTech.mobileapp.reservation;
import java.util.List;
import java.util.Optional;
import	java.util.ArrayList;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.doctor.list_patient.DoctorPatients;
import com.nizar.SahTech.doctor.list_patient.DoctorPatientsRepository;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.following_list_doctor.followinglist;
import com.twilio.rest.chat.v1.service.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    final private ReservationRepository reservationRepository;
    final private DoctorRepo doctorRepo;
    final private UserRepository userRepository;
        private final DoctorPatientsRepository doctorPatientsRepository;

    //add reservation
 public   ResponseEntity<String> addReservation(ReservationDTO reservationDTO) {
    Optional<DoctorEntity> doctor = doctorRepo.findById(reservationDTO.getId_praticien());
    Optional<UserEntity> user = userRepository.findByUsername(reservationDTO.getPatientemail());
    
        Reservation reservation = new Reservation();
        try {
            reservation.setJour(reservationDTO.getJour());
            reservation.setHeure(reservationDTO.getHeure());
            reservation.setUsername(user.get().getEmail());
            reservation.setDoctorname(doctor.get().getEmail());
            reservation.setIdpatient(user.get().getId());
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
                    if (reservation.getCompleted() == 0 && reservation.isCancel() == false) {
                        ReservationDTO reservationDTO = reservationList(reservation);
                        reservations.add(reservationDTO);

                    }
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
    
    
            dto.setId(reservation.getId());
            dto.setPatientname(user.get().getEmail());
            dto.setPatientemail(user.get().getUsername());
            dto.setId_patient(reservation.getIdpatient());
            dto.setJour(reservation.getJour());
            dto.setHeure(reservation.getHeure()); 
            dto.setPatientphone(user.get().getPhone());
                return dto;
    }

    public ResponseEntity<?> completeTheReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        try {
            if (reservation.isPresent()) {
                reservation.get().setCompleted(1);
                reservationRepository.save(reservation.get());
                Optional<DoctorPatients> patient = doctorPatientsRepository.findById(reservation.get().getIddoctor());
                 if (patient.get().getPatientsList() != null) {
                    List<String> patientIds = new ArrayList<>();
                    byte[] patientIdBytes = patient.get().getPatientsList();
                    String patientIdString = new String(patientIdBytes);
                    String[]patientIdLines =patientIdString.split("\n");
                    for (String line : patientIdLines) {
                        patientIds.add(line);
                    }
                    patientIds.add ( reservation.get().getIdpatient()); ;
                    StringBuilder newPatientIds = new StringBuilder();
                    for (String Id : patientIds) {
                        newPatientIds.append(Id).append("\n");
                    }
                    byte[] newPatientIdsBytes = newPatientIds.toString().getBytes();
                    patient.get().setPatientsList(newPatientIdsBytes);
                    patient.get().setPatientsCount(patient.get().getPatientsCount() + 1);
                    doctorPatientsRepository.save(patient.get());
                    return ResponseEntity.ok("Patient added successfully");
            
                 }
                 
            }
            return ResponseEntity.ok("Reservation not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to complete the reservation: " + e.getMessage());
        }
    }
// update reservation
    public ResponseEntity<?> updateReservation(ReservationDTO reservationDTO) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationDTO.getId());
        try {
            if (reservation.isPresent()) {
                reservation.get().setJour(reservationDTO.getJour());
                reservation.get().setHeure(reservationDTO.getHeure());
                reservationRepository.save(reservation.get());
                return ResponseEntity.ok("Reservation updated successfully");
            }
            return ResponseEntity.ok("Reservation not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update the reservation: " + e.getMessage());
        }
    }

        
        
    
     
}
