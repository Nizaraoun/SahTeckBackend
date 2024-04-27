package com.nizar.SahTech.users.medical_doc;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nizar.SahTech.users.Auth.UserEntity;

import java.util.Optional;
import java.util.List;

public interface DocRepository extends JpaRepository<Document, String> {

    Optional<Document> findByDescription(String description);
    List<Document> findByUserId(String userId);
    Optional<Document> findByUserIdAndDescription(String userId, String description);
    Optional<Document> findByDescriptionAndUserId(String discription, String userId);

}