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
    @Column(name = "Id")
    private String id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "DateOfBirth")
    private String dateOfBirth;
    
    @Column(name = "Image")
    private  String  Image;
    @Column(name = "IsActive")
    private boolean isActive ;

// 

    @Temporal(TemporalType.DATE)
    private String creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();


    //image

    // @OneToOne(mappedBy = "userEntity")
    // private UserImage userImage;


}
