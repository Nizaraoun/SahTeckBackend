// package com.nizar.SahTech.users.controller;


// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.nizar.SahTech.users.Auth.AuthResponseDTO;
// import com.nizar.SahTech.users.Auth.AuthenticationDTO;
// import com.nizar.SahTech.users.Auth.UserDTO;
// import com.nizar.SahTech.users.dto.HelloResponse;
// import com.nizar.SahTech.users.service.userservice;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api")
// @RequiredArgsConstructor
// public class HelloController {
//    private final userservice userService;

//     @PostMapping("/hey")
//     public ResponseEntity<?> login(@RequestBody UserDTO user )  {
             
       
//         try {
//             System.out.println("----------------------");
//             System.out.println(user.getId());
//                 userService.saveImageForUser(user.getId(), user.getImage());
//                 return ResponseEntity.ok("Image uploaded successfully for user with ID: " );
//             } catch (Exception e) {
//                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
//             }
//     }


// }