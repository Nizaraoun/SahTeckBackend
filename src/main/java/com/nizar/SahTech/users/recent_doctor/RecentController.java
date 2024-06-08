package com.nizar.SahTech.users.recent_doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nizar.SahTech.doctor.entity.DoctorEntity;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RecentController {


    private final RecentDoctorService   recentDoctorService;

    @GetMapping("/get-recent-doctors")
    public List<DoctorEntity> getDoctor(@RequestParam String uid)
    {
        return recentDoctorService.getDoctor(uid);

    }
    
}
