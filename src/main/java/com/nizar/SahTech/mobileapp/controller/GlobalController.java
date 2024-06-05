package com.nizar.SahTech.mobileapp.controller;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nizar.SahTech.doctor.entity.DoctorEntity;
import com.nizar.SahTech.mobileapp.dto.RatingDTO;
import com.nizar.SahTech.mobileapp.entity.Announcement;
import com.nizar.SahTech.mobileapp.service.FileStorageService;
import com.nizar.SahTech.mobileapp.service.GlobalAppService;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GlobalController {
    //Injecting part
    private final  GlobalAppService appService ;
    private final FileStorageService fileStorageService;


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
@GetMapping("/get_doctor_By_Speciality")
public List<DoctorEntity> getDoctorBySpeciality( @RequestParam String speciality) {
    return appService.GetDoctorBySpeciality(speciality);}

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("document_name") String documentName,@RequestParam("role") String role ,Principal principal) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("No file selected to upload!");
        }
        principal.getName();
        String message = fileStorageService.saveFile(file , documentName,role,principal.getName());
        return ResponseEntity.ok(message);
    }


}

