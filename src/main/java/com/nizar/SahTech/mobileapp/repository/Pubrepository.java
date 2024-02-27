package com.nizar.SahTech.mobileapp.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.nizar.SahTech.mobileapp.entity.Announcement;



@Repository
public interface Pubrepository extends JpaRepository<Announcement, Long>{
    List<Announcement> findByisAcceptedTrue();
  
}
