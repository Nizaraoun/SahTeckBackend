package com.nizar.SahTech.admin.report;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Table(name = "report_section")
@Entity
public class report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private long reportId;
    @Column(name = "report_subject")
    private String reportSubject;
    @Column(name = "Sender_id")
    private String senderId;
    @Column(name = "date")
    private String date;
    @Column(name = "role")
    private String role;
    @Column(name = "status")
    private boolean status;
}
