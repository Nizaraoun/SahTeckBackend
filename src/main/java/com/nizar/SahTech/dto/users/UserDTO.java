package com.nizar.SahTech.dto.users;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String cin;

    private String phone;

    private String password;

    private boolean active;

}
