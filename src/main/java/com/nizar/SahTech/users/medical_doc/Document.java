package com.nizar.SahTech.users.medical_doc;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Table(name = "Document_Names")

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "doc_id")
    private Long id;
    @Column(name = "number_files")
    private int number;
    @Column(name = "description")
    private String description;;
    @Column(name = "user_id")
    private String userId;
}
