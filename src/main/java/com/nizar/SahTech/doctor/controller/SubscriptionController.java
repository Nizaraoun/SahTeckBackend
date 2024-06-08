package com.nizar.SahTech.doctor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nizar.SahTech.doctor.entity.DoctorEntity.Plan;
import com.nizar.SahTech.doctor.service.subscriptionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class SubscriptionController {
    private final subscriptionService subscriptionService;


    @GetMapping("/get-subscription")
    public String getSubscription(@RequestParam String uid)
    {
        return subscriptionService.getSubscription(uid);

    }
    @PostMapping("/add-subscription")
    public String addSubscription(@RequestParam String id , @RequestParam Plan plan) {
        return  subscriptionService.addSubscription(id,plan);
           
    }
    
}
