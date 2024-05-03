package com.nizar.SahTech.users.Auth.Login;
import com.nizar.SahTech.security.JWTGenerator;
import com.nizar.SahTech.users.Auth.AuthResponseDTO;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
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


@RestController
@RequiredArgsConstructor
// @RequestMapping("/api/users")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
private final UserRepository userRepository;


    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authenticationDTO ) {
        try {
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(),
                        authenticationDTO.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
           Optional<UserEntity> user = userRepository.findByUsername(authenticationDTO.getEmail());
           user.get().setPassword(null);
           
            return new ResponseEntity<>(new AuthResponseDTO(token, user) , HttpStatus.OK);

        } catch (AuthenticationException e) {
            // Authentication failed, return an error message
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }




}
