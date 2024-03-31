package com.nizar.SahTech.users.Auth;

import jakarta.persistence.*;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.nizar.SahTech.role.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_section")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming ID is auto-generated
    @Column(name = "Id")
    private Long id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Cin")
    private String cin;
    @Column(name = "Image")
    private  String Image;
    @Column(name = "IsActive")
    private boolean isActive ;

// 

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();


    //image

    // @OneToOne(mappedBy = "userEntity")
    // private UserImage userImage;


}
