package com.nizar.SahTech.users.Auth.Otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
	private String username;
    private String email;
    private String body;
    private String otp;

}