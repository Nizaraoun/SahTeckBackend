package com.nizar.SahTech.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.admin.service.webService;
import com.nizar.SahTech.doctor.entity.DoctorEntity;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class webController {
    private final webService webService;

    @GetMapping("/get-all-doctors")
    public List<DoctorEntity> getAllDoctors() {
        return  webService.getAllDoctors();
    } 
    @GetMapping("/get-doctor-by-id")
    public DoctorEntity getDoctorById(@RequestParam String id) {
        return webService.getDoctorById(id);
    }
    @GetMapping("/get-doctor-by-username")
    public DoctorEntity getDoctorByUsername(@RequestParam String username) {
        return webService.getDoctorByUsername(username);
    }
    @GetMapping("/get-new-doctor")
    public List<DoctorEntity> getNewDoctor() {
        return webService.getNewDoctor();
    }
    @GetMapping("/approuver-doctor")
    public DoctorEntity approuverDoctor (@RequestParam String id) {
        return webService.approuverDoctor(id);
    }
    @GetMapping("/desapprouver-doctor")
    public String desapprouverDoctor (@RequestParam String id) {
        return webService.desapprouverDoctor(id);
    }

    @GetMapping("/delete-doctor")
    public String deleteDoctor(@RequestParam String id) {
        return webService.deleteDoctor(id);
    }
    @GetMapping("/delete-pub")
    public String deletePub(@RequestParam Long id) {
        return webService.deletePub(id);
    }
    
    
}
