package com.nizar.SahTech.mobileapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.mobileapp.dto.Announce;
import com.nizar.SahTech.mobileapp.entity.Announcement;
import com.nizar.SahTech.mobileapp.service.AppService;

import lombok.RequiredArgsConstructor;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GetPubController {
    private final  AppService appService ;
    @GetMapping("/publication")
    public List<Announcement> publication() {
        return appService.GetPublication(); 
          
    }


    
}
