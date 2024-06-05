package com.nizar.SahTech.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class appService {
    private final UserRepository userRepository;

    public  List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        users.forEach(user -> user.setPassword(null)
        );
        return users;
        
    }

    public UserEntity getUserById(String id) {
       Optional<UserEntity> users = userRepository.findById(id);
         if(users.isPresent()){
              UserEntity user = users.get();
              user.setPassword(null);
              user.setRoles(null);
              return user;
         }
         return null;
    }

    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> users = userRepository.findByUsername(username);
        if(users.isPresent()){
            UserEntity user = users.get();
            user.setPassword(null);
            user.setRoles(null);

            return user;
        }
        return null;
        
    }

    public String deleteUser(String id) {
        userRepository.deleteById(id);
        return "User deleted";
    }


    
    
}
