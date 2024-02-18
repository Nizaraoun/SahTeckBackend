package com.nizar.SahTech.dto.users;
public record AuthenticationResponse(String jwtToken , String email , String name , String cin , String phone , boolean active) {

}
