package com.nizar.SahTech.users.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import com.nizar.SahTech.users.Auth.UserDTO;
import com.nizar.SahTech.users.medical_doc.Document;
import com.nizar.SahTech.users.medical_doc.DocumentDTO;
import com.nizar.SahTech.users.medical_doc.MedicalFile;
import com.nizar.SahTech.users.medical_doc.MedicalFileDTO;
import com.nizar.SahTech.users.medical_doc.fileDTO;
import com.nizar.SahTech.users.medical_doc.userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddPicture {
    private final userservice userService;
   
        // this method is used to get the image for the user

        @GetMapping("/get-image")
        public ResponseEntity<?> getImageForUser(Principal connecteduser) {
            return userService.getImageForUser(connecteduser);
        } 

        // this method is user to  add the document for the user

        @PostMapping("/add-medical-doc")
        public ResponseEntity<?> addMedicalDocForUser(@RequestBody DocumentDTO documentDTO, Principal connecteduser) {
            return userService.addMedicalDocForUser(documentDTO, connecteduser);
        }

        // this method is user to get the document for the user
            @GetMapping("/get-medical-doc")
            public List<Document> getMedicalDocForUser(Principal connecteduser) {
                return userService.getMedicalDocForUser(connecteduser);
            }

            // this method is used to delet the medical file for the user

            @DeleteMapping("/delete-medical-doc")
            public ResponseEntity<?> deleteMedicalDocForUser(@RequestParam String docId, Principal connecteduser) {
                return userService.deleteMedicalFileForUser(connecteduser,docId);
            }

            // this method is used to save the medical file for the user

             @PostMapping("/add-medical-file")   
            public ResponseEntity<?> uploadMedicalFileForUser(@RequestParam("file") MultipartFile file, @RequestParam("document_id") String id ,Principal principal) {
            return userService.saveMedicalFileForUser( principal,id,file );
             } 

             @PostMapping("/get-medical-file")
                public ResponseEntity< ?> getMedicalFileForUser (@RequestBody fileDTO filedto ,Principal connecteduse ) {

                    return userService.getMedicalFileForUser(filedto, connecteduse);
                }
    }


    
    


