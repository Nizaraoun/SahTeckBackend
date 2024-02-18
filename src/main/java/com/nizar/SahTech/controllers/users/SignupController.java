package com.nizar.SahTech.controllers.users;
import com.nizar.SahTech.dto.users.SignupDTO;
import com.nizar.SahTech.dto.users.UserDTO;
import com.nizar.SahTech.services.users.auth.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private userservice userservice;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
       UserDTO  createdUser = null;
       System.out.println("signupDTO: " + signupDTO);
    try {

        createdUser = userservice.createUser(signupDTO);
    } catch (Exception e) {
        e.printStackTrace();
    }

       if (createdUser == null){
           return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}

