package com.nizar.SahTech.mobileapp.entity;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "announcement_section")
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    @Column(name = "Id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "Image")
    private String image;
    @Column(name = "doctor_id")
    private Long doctorId;
    @Column(name = "doctor_name")
    private String doctorName;
    @Column(name = "patient_id")
    private Long patientId;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "is_accepted")
    private Boolean isAccepted;
}
