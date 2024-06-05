package com.nizar.SahTech.users.Auth.update;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.chat.v1.service.User;

import lombok.AllArgsConstructor;
import java.util.Optional;


@Service
@AllArgsConstructor
public class update_password_service {
         private final PasswordEncoder passwordEncoder;
            private final UserRepository userRepository;

    public ResponseEntity<String> updatePassword( String password , String email) {

        Optional<UserEntity> user = userRepository.findByUsername(email);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.save(userEntity);
            return ResponseEntity.ok("Password updated successfully");
        }
        return null;    
        
    }
    
}
