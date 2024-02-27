package com.nizar.SahTech.users.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SignupController {

    private final AuthService authService;
      @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody SignupDTO signupDTO) {
        return authService.register(signupDTO);
    }
}

