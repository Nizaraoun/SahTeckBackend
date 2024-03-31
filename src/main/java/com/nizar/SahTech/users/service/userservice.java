package com.nizar.SahTech.users.service;
import java.util.Optional;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.users.Auth.UserDTO;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.dto.MedicalFileDTO;
import com.nizar.SahTech.users.entite.MedicalFile;
import com.nizar.SahTech.users.repository.DocFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userservice {
    private final UserRepository userRepository; // Assuming you have a repository for users
private final DocFileRepository docFileRepository; // Assuming you have a repository for medical files

   
// this method is used to save the profile image for the user
  public ResponseEntity<String> saveImageForUser(UserDTO imageData ,Principal connecteduser) {

Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
try {
    if (user.isPresent()) {
        user.get().setImage(imageData.getImage());
        userRepository.save(user.get());
        return ResponseEntity.ok("Image uploaded successfully for user with ID: " + user.get().getId());
    }
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
}
return ResponseEntity.badRequest().body("User not found");
  }


  // this method is used to save the medical file for the user

public ResponseEntity<String> saveMedicalFileForUser(MedicalFileDTO medicalFileData, Principal connecteduser) {

    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    MedicalFile medicalFile = new MedicalFile();
try {
    if (user.isPresent()) {
        medicalFile.setFile(medicalFileData.getFile());
        medicalFile.setName(medicalFileData.getName());
        medicalFile.setDescription(medicalFileData.getDescription());
        medicalFile.setUserId(user.get().getId());
        docFileRepository.save(medicalFile);
    
    
        return ResponseEntity.ok("Medical file uploaded successfully for user with ID: " + user.get().getId());
    }
    
    
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Medical File: " + e.getMessage());
}
return ResponseEntity.badRequest().body("User not found");

}
// this method is used to get the profile image for the user
public ResponseEntity<?> getImageForUser(Principal connecteduser) {
    Optional<UserEntity> user = userRepository.findByUsername(connecteduser.getName());
    try {
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getImage());
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get image: " + e.getMessage());
    }
    return ResponseEntity.badRequest().body("User not found");}

}


  
