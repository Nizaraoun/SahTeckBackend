package com.nizar.SahTech.doctor.entity;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.nizar.SahTech.doctor.list_patient.DoctorPatients;
import com.nizar.SahTech.role.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Doctor_section")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
  public enum Plan {
    FREE, BASIC, Standard, Premium
}
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Speciality")
    private String speciality;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "password")
    private String password;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "image")
    private String image;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "followers")
    private Integer followers;
    @Column(name = "bio")
    private String bio;
    @Column(name = "subscription_plans")
    private Plan subscription;
    
      @Temporal(TemporalType.DATE)
    private String creationDate;
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

  
}
