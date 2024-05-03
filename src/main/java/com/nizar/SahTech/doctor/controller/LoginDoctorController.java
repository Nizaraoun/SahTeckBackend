package com.nizar.SahTech.doctor.controller;
import com.nizar.SahTech.doctor.repository.DoctorRepo;
import com.nizar.SahTech.security.JWTGenerator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nizar.SahTech.doctor.dto.AuthDrResponseDTO;
import com.nizar.SahTech.doctor.entity.DoctorEntity;

@RestController
@RequiredArgsConstructor
public class LoginDoctorController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
private final DoctorRepo doctorRepository;
    @PostMapping("/login")
    public ResponseEntity<?> logindoctor(@RequestBody DrAuthenticationDTO authenticationDTO ) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getEmail(),
                    authenticationDTO.getPassword()
                )

        );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
           Optional<DoctorEntity> doctor = doctorRepository.findByUsername(authenticationDTO.getEmail());
           doctor.get().setPassword(null);

            return new ResponseEntity<>(new AuthDrResponseDTO(token, doctor) , HttpStatus.OK);

        } catch (AuthenticationException e) {
            System.out.println(e);
            // Authentication failed, return an error message
            return new ResponseEntity<>("Invalid username or password for doctor ", HttpStatus.UNAUTHORIZED);
        }
    }




}
