package com.nizar.SahTech.users.controller;
import javax.naming.AuthenticationException;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

import com.nizar.SahTech.security.JWTGenerator;
import com.nizar.SahTech.users.Auth.AuthResponseDTO;
import com.nizar.SahTech.users.Auth.UserDTO;
import com.nizar.SahTech.users.dto.MedicalFileDTO;
import com.nizar.SahTech.users.entite.MedicalFile;
import com.nizar.SahTech.users.service.userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddPicture {
    private final userservice userService;
    private final JWTGenerator jwtGenerator;
    @PostMapping("/addProfileimg")
    public ResponseEntity<?> uploadImageForUser(@RequestBody UserDTO imageData ,Principal connecteduser) {
        System.out.println(connecteduser.getName()  );
        try {
        System.out.println("++++++++++++++++++++++++");
            userService.saveImageForUser(imageData, connecteduser);
            // jwt=jwtGenerator.generateToken(authentication);
            return ResponseEntity.ok("Image uploaded successfully for user with ID: " + connecteduser.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }

    @PostMapping("/AddMedicalFile")
    public ResponseEntity<?> uploadMedicalFileForUser(@RequestBody MedicalFileDTO medicalFileData ,Principal connecteduser) {
          userService.saveMedicalFileForUser(medicalFileData, connecteduser);
            return userService.saveMedicalFileForUser(medicalFileData, connecteduser);
        }

    }


    
    


