package com.nizar.SahTech.users.medical_doc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
public interface DocRepository extends JpaRepository<Document, String> {

    Optional<Document> findByDescription(String description);
    List<Document> findByUserId(String userId);
    Optional<Document> findByUserIdAndDescription(String userId, String description);
    Optional<Document> findByDescriptionAndUserId(String discription, String userId);
    Optional<Document> findByUserIdAndDocCode(String userId, String code);

}