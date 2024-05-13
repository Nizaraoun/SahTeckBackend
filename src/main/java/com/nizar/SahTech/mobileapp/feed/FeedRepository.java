package com.nizar.SahTech.mobileapp.feed;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;
public interface FeedRepository  extends JpaRepository<Feed, Long>{

    List<Feed> findAllByUserRole(String role);

    Optional<Feed> findByUserId(String line);

    List<Feed> findAllByUserId(String userId); 

} 