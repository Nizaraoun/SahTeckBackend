package com.nizar.SahTech.mobileapp.chat;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;


@Repository

public interface ChatRepository  extends JpaRepository<Chat, String>{

    Long findIdByDoctorIdAndUserId(String doctorId, String userId);
    Optional<Chat> findByDoctorIdAndUserId(String doctorId, String userId);
    List<Chat> findByDoctorId(String id);
    List<Chat> findByUserId(String id);

}
