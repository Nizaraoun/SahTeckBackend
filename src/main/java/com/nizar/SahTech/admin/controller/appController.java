package com.nizar.SahTech.admin.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nizar.SahTech.admin.service.appService;
import com.nizar.SahTech.users.Auth.UserEntity;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class appController {
    private final appService appService;

    @GetMapping("/get-all-users")
    public List<UserEntity> getAllUsers() {
        return  appService.getAllUsers();
    }
    @GetMapping("/get-user-by-id")
    public UserEntity getUserById(@RequestParam String id) {
        return appService.getUserById(id);
    }
    @GetMapping("/get-user-by-username")
    public UserEntity getUserByUsername(@RequestParam String username) {
        return appService.getUserByUsername(username);
    }
    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam String id) {
        return appService.deleteUser(id);
    }


}