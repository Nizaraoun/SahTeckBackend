package com.nizar.SahTech.mobileapp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.mobileapp.dto.Announce;
import com.nizar.SahTech.mobileapp.dto.RatingDTO;
import com.nizar.SahTech.mobileapp.entity.Announcement;
import com.nizar.SahTech.mobileapp.service.AppService;

import lombok.RequiredArgsConstructor;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetPubController {
    //Injecting part
    private final  AppService appService ;

    //Get all publication to the app user
    @GetMapping("/publication")
    public List<Announcement> publication() {
        return appService.GetPublication(); 
    }

    //Get all doctors to the app user
    @GetMapping("/getdoctor")
    public List<DoctorEntity> getdoctor() {
        return appService.findallDoctor();
}
@PostMapping("/addRating")
public ResponseEntity<String> addRating(@RequestBody RatingDTO ratingDTO) {
    
    return appService.AddRating(ratingDTO.getId(), ratingDTO.getRate());
}
@GetMapping("/recommended_doctors")
public List<DoctorEntity> recommendation() {
    return appService.GetRecommended_Doctors();
}
}
