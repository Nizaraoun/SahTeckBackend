package com.nizar.SahTech.users.Auth;
import java.sql.Date;
import java.util.Collections;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nizar.SahTech.role.dto.Role;
import com.nizar.SahTech.role.repository.RoleRep;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {
        private final UserRepository userRepository;
    private final RoleRep roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(SignupDTO signupDTO) {
        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        // Check if the email is already registered
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            return new ResponseEntity<>("Email is already registered!", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(signupDTO.getUsername());
        user.setPhone(signupDTO.getPhone());
        user.setCin(signupDTO.getCin());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        user.setCreationDate(new Date(0));
        Optional<Role> userRoleOptional = roleRepository.findByName("USER");
        if (userRoleOptional.isEmpty()) {
            return new ResponseEntity<>("Error during registration. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Role userRole = userRoleOptional.get();
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
    
}
