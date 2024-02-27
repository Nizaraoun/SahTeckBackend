package com.nizar.SahTech.doctor.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nizar.SahTech.doctor.dto.SignUpDto;
import com.nizar.SahTech.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class SigninDoctorCotroller {

    private final DoctorService doctorService;
    @PostMapping("/registerdoctor")
    public ResponseEntity<String> register(@RequestBody SignUpDto signupDTO) {
       
        return doctorService.registerDoctor(signupDTO);
    }

}
