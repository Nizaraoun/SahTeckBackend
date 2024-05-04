package com.nizar.SahTech.mobileapp.reservation;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    @Query("SELECT r.heure FROM Reservation r WHERE r.iddoctor = :idPraticien AND r.jour = :jour")
    List<String> findReservedHoursForDoctorOnDay(@Param("idPraticien") String idPraticien, @Param("jour") String jour);

    Optional<List<Reservation>> findReservationsByIdpatient(String userId);
    Optional<List<Reservation>> findByIddoctor(String idPraticien);
}
