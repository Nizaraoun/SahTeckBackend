package com.nizar.SahTech.users.following_list_doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class followingController {
    private final followingService followingService;

    @PostMapping("/add-following")
    public ResponseEntity<?> addfollow(@RequestParam String userid ,String id_doctor) {
        return followingService.addfollow(userid, id_doctor);
    }

    @GetMapping("/is-following")
    public ResponseEntity<Boolean> is_followed(@RequestParam String userid, String id_doctor) {
        return followingService.is_followed(userid, id_doctor);
    }

    
}
