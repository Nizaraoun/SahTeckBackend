package com.nizar.SahTech.doctor.dto;
import lombok.Data;

@Data
public class SignUpDto {
    private String phone;
    private String email;
    private String username;
    private String password;
    private String specialty;
    private String image;
    private String followrs;
}
