package com.nizar.SahTech.users.medical_doc;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Table(name = "medical_file")
@Entity
public class MedicalFile {
    @Id
    @Column(name = "doc_id")
    private String id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "file", length = 1000000000)
    private byte[] file;
    @Column(name = "user_id")
    private String userId;

   
    
}
