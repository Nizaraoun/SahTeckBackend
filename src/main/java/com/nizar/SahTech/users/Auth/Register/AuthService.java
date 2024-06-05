package com.nizar.SahTech.users.Auth.Register;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nizar.SahTech.role.dto.Role;
import com.nizar.SahTech.role.repository.RoleRep;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.nizar.SahTech.users.Auth.Otp.OtpRequest;
import com.nizar.SahTech.users.Auth.Otp.SmsService;
import com.nizar.SahTech.util.IdGenerator;
import com.twilio.twiml.voice.Sms;

import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final RoleRep roleRepository;
    private final SmsService smsService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(SignupDTO signupDTO) {
        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            return new ResponseEntity<>("email is taken!", HttpStatus.BAD_REQUEST);
        }
        // Check if the email is already registered
        // if (userRepository.existsByEmail(signupDTO.getEmail())) {
        //     return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        // }
        // Check if the phone number is already registered
        if (userRepository.existsByPhone(signupDTO.getPhone())) {
            return new ResponseEntity<>("Phone number is already registered!", HttpStatus.BAD_REQUEST);
            
        }
        else {
            LocalDateTime createdAt = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedCreatedAt = createdAt.format(formatter);
    
            String otp= smsService.generateOTP();

            String body = String.format(
				"Dear :"+signupDTO.getEmail()+"\n"+
				"  رقم التأكيد الخاص بك هو   " + otp + " "+ "أدخله لتأكيد حسابك \n"+
				"  شكرا لاختياركم  هذه المنصة  \n"
			
            );
            smsService.SendMail( new OtpRequest(signupDTO.getUsername(),  signupDTO.getEmail() , body, otp));
   UserEntity user = new UserEntity();
        user.setUsername(signupDTO.getUsername());
        user.setId(IdGenerator.generateId(24));
        user.setPhone(signupDTO.getPhone());
        user.setCin(signupDTO.getCin());
        user.setImage("default.jpg");
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setCreationDate(formattedCreatedAt);
        Optional<Role> userRoleOptional = roleRepository.findByName("USER");
        if (userRoleOptional.isEmpty()) {
            return new ResponseEntity<>("Error during registration. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Role userRole = userRoleOptional.get();
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);

            return new ResponseEntity<>("OTP sent successfully", HttpStatus.OK);
        }


    }
    
}
