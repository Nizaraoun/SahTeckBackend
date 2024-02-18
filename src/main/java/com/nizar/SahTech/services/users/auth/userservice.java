package com.nizar.SahTech.services.users.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.dto.users.SignupDTO;
import com.nizar.SahTech.dto.users.UserDTO;
import com.nizar.SahTech.repositories.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userservice {

   private final UserRepository userRepository;
   
   @Value("${app.client.url}")
   private String appClientUrl;

   public UserDTO createUser(SignupDTO signupDTO)  throws Exception 
    { 
       
       if (userRepository.findFirstByEmail(signupDTO.getEmail()) == null) {
        try {
           return userRepository.createUser(signupDTO);
        } catch (Exception e) {
           throw new Exception("User not created, come again later!");}
   }

    return null;}

    // Ahtenticate user
      public UserDTO authenticateUser(String email, String password) {
      return userRepository.authenticateUser(email, password);


      }

   public void isActiveSesion(String email) {
      userRepository.isActiveSesion(email);
}}
