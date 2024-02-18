package com.nizar.SahTech.controllers.users;


import com.nizar.SahTech.dto.users.AuthenticationDTO;
import com.nizar.SahTech.dto.users.AuthenticationResponse;
import com.nizar.SahTech.dto.users.UserDTO;
import com.nizar.SahTech.services.users.auth.userservice;
import com.nizar.SahTech.services.users.jwt.UserDetailsServiceImpl;
import com.nizar.SahTech.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
// @RequestMapping("/api/users")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userservice userDetailsService;

    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDTO userDetails = userDetailsService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());

        final String jwt = jwtUtil.generateToken(userDetails.getEmail());
        
        System.out.println("name: " + userDetails);
        

        return new AuthenticationResponse(jwt ,  userDetails.getEmail() ,userDetails.getName() , userDetails.getPhone() , userDetails.getCin() , userDetails.isActive());

    }

}
