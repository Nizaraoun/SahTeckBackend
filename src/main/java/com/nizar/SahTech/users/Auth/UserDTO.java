package com.nizar.SahTech.users.Auth;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String cin;

    private String phone;

    private String password;

    private String role;

    private String image;

}
