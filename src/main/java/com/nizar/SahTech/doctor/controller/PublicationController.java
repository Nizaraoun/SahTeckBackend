package com.nizar.SahTech.doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.doctor.dto.PublicationDTO;

@RequestMapping("/api/publication")
@RestController
public class PublicationController {
    @PostMapping("/addpub")
    public ResponseEntity<?> addPublication(@RequestBody PublicationDTO publicationDTO) {


        return ResponseEntity.ok("Publication added");
    }
    
}
