package com.nizar.SahTech.users.Auth.update;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.users.Auth.UserDTO;
import com.twilio.rest.chat.v1.service.User;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

public class update_password_controller {
            private final update_password_service update_password_service;
@PostMapping("/update-password")
    public ResponseEntity<String> updatePassword  ( @RequestBody UserDTO userDTO) {

    return    update_password_service.updatePassword( userDTO.getPassword(), userDTO.getEmail());
    }

    
}
