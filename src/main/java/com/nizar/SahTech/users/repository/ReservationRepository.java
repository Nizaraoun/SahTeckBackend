package com.nizar.SahTech.users.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.nizar.SahTech.users.entite.Reservation;



@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    @Query("SELECT r.heure FROM Reservation r WHERE r.id_praticien = :idPraticien AND r.jour = :jour")
    List<String> findReservedHoursForDoctorOnDay(@Param("idPraticien") Long idPraticien, @Param("jour") String jour);
}