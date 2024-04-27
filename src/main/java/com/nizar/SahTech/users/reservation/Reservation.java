package com.nizar.SahTech.users.reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Table(name = "Reservation_section")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "Id")
    private Long id;
    @Column(name = "Jour")
    private String jour;
    @Column(name = "Heure")
    private String heure;
    @Column(name = "Username")
    private String username;
    @Column(name = "Doctorname")
    private String doctorname;
    @Column(name = "Id_patient")
    private String idpatient;
    @Column(name = "Id_praticien")
    private String  id_praticien;
    @Column(name = "cancel")
    private boolean cancel;
    @Column(name = "Completed")
    private double completed;
    @Column(name = "Specialty")
    private String specialty;
    
}
