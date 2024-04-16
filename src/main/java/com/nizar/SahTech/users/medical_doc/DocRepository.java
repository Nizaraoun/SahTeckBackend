package com.nizar.SahTech.users.medical_doc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface DocRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByDescription(String description);
    List<Document> findByUserId(String userId);

}