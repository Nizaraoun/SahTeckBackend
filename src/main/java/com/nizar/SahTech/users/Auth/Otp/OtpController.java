package com.nizar.SahTech.users.Auth.Otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/otp")
@Slf4j
@RequiredArgsConstructor
public class OtpController {

	@Autowired
	private final SmsService smsService;
	
	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	} 

	@PostMapping("/send-otp")
	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
		log.info("inside sendOtp :: "+otpRequest.getUsername());
		otpRequest.setOtp(smsService.generateOTP());
		otpRequest.setBody("Your OTP is : "+otpRequest.getOtp()+"\n"+"Please do not share this OTP with anyone. Sah'Tech "+"\n"+"and don't forget your password again xD" );
		return smsService.SendMail(otpRequest);
	}
	@PostMapping("/validate-otp")
	public ResponseEntity<?> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
		return smsService.validateOtp(otpValidationRequest);
    }


	
}