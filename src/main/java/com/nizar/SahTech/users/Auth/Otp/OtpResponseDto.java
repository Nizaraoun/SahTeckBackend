package com.nizar.SahTech.users.Auth.Otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponseDto {
	private OtpStatus status;
    private String message;
}