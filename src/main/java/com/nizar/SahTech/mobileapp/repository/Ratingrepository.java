package com.nizar.SahTech.mobileapp.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nizar.SahTech.mobileapp.entity.Rating;
@Repository
public interface Ratingrepository extends JpaRepository<Rating, Long>{
    Optional<Rating> findByDoctorId(String doctorId);
    @Query("SELECT r.doctorId FROM Rating r ORDER BY r.rate DESC LIMIT 15")
    List<String> findTop15DoctorIdsByRatingDesc();

}
