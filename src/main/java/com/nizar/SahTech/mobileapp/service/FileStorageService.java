package com.nizar.SahTech.mobileapp.service;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.chat.v1.service.User;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final DoctorRepo doctorRepository;
 private final UserRepository userRepository;
    private final String rootDir = "uploads/";

    private void createDirectory(String dir) {
        Path path = Paths.get(dir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + dir);
            } else {
                System.out.println("Directory already exists: " + dir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory: " + dir, e);
        }
    }

    public String saveFile(MultipartFile file, String documentName , String role , String username) {
        String uploadDir = rootDir + role + "/" + documentName + "/";
        createDirectory(uploadDir); // Ensure the directory is created before saving the file

        try {
            Path copyLocation = Paths.get(uploadDir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            if (role.equals("doctor")) {
                doctorRepository.findByUsername(username).get().setImage(documentName+"/"+file.getOriginalFilename());
                doctorRepository.save(doctorRepository.findByUsername(username).get());
                
            }
            else if (role.equals("user")) {
                userRepository.findByUsername(username).get().setImage(documentName+"/"+file.getOriginalFilename());
                doctorRepository.save(doctorRepository.findByUsername(username).get());
            }
            return  documentName+"/"+file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not store file " + file.getOriginalFilename() + " in " + documentName + " directory. Please try again!";
        }
    }
    
    public Resource loadFileAsResource(String filename, String role, String documentName) {
        try {
            Path filePath = Paths.get(rootDir).resolve(role).resolve(documentName).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found " + filename, e);
        }
    }

}
