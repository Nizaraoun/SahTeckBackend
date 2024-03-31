package com.nizar.SahTech.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

import com.nizar.SahTech.security.JWTGenerator;
import com.nizar.SahTech.users.Auth.UserDTO;
import com.nizar.SahTech.users.dto.MedicalFileDTO;
import com.nizar.SahTech.users.service.userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddPicture {
    private final userservice userService;
    // this method is used to save the profile image for the user
    @PostMapping("/addProfileimg")
    public ResponseEntity<?> uploadImageForUser(@RequestBody UserDTO imageData ,Principal connecteduser) {
        System.out.println(connecteduser.getName()  );
        try {
            if (imageData == null || imageData.getImage() == null || imageData.getImage().length() == 0) {
                return ResponseEntity.badRequest().body("Image is required");
            }
            else{

                userService.saveImageForUser(imageData, connecteduser);
                return ResponseEntity.ok("Image uploaded successfully for user with ID: " + connecteduser.getName());

            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }
    }
   
        // this method is used to get the image for the user

        @GetMapping("/get-image")
        public ResponseEntity<?> getImageForUser(Principal connecteduser) {
            return userService.getImageForUser(connecteduser);
        }

        // this method is used to save the medical file for the user
 @PostMapping("/AddMedicalFile")
    public ResponseEntity<?> uploadMedicalFileForUser(@RequestBody MedicalFileDTO medicalFileData ,Principal connecteduser) {
          userService.saveMedicalFileForUser(medicalFileData, connecteduser);
            return userService.saveMedicalFileForUser(medicalFileData, connecteduser);
        }
    }


    
    


