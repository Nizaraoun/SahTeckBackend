package com.nizar.SahTech.users.Auth;

import lombok.Data;
import java.util.List;
import java.util.Optional;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private Optional <UserEntity> user;

    public AuthResponseDTO(String accessToken , Optional<UserEntity> user2) {
        this.accessToken = accessToken;
        this.user = user2;
    }



}
