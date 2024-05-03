package com.nizar.SahTech.doctor.entity;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "Latitude")
    private String latitude;
    @Column(name = "Longitude")
    private String longitude;
    @Column(name = "password")
    private String password;
    @Column(name = "is_active")
    private Boolean isActive;
    @Lob
    @Column(name = "image" ,length = 20000000)
    private byte[] image;
    @Column(name = "rating")
    private Double rating;
    
      @Temporal(TemporalType.DATE)
    private Date creationDate;
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();

}
