package com.nizar.SahTech.users.Auth.Otp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.security.TwilioConfig;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class SmsService  {

    @Autowired
    private JavaMailSender emailSender;

	private final TwilioConfig twilioConfig;
	private final UserRepository userRepository;
    Map<String, String> otpMap = new HashMap<>();



	// public OtpResponseDto sendSMS(OtpRequest otpRequest) {
	// 	OtpResponseDto otpResponseDto = null;
	// 	try {
	// 		String User = otpRequest.getUsername();
	// 		PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
	// 		PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
	// 		String otp = generateOTP();
	// 		String otpMessage = "أهلا بك في Sah'tech , رقم التأكيد الخاص بك هو   " + otp + " ";
	// 		Message message = Message
	// 		        .creator(to, from,
	// 		                otpMessage)
	// 		        .create();
	// 		otpMap.put(otpRequest.getUsername(), otp);
	// 		otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
	// 	}
	// 	return otpResponseDto;
	// }
	
	public ResponseEntity<?> validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys = otpMap.keySet();
		String username = null;
		for(String key : keys)
			username = key;
        if (otpValidationRequest.getUsername().equals(username) && otpMap.get(username).equals(otpValidationRequest.getOtpNumber())) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
		try {
UserEntity	user = userRepository.findByUsername(username).get();
			user.setActive(true);
			userRepository.save(user);
			return ResponseEntity.ok("OTP is valid!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("catch error!");
		}
		

        }
		return ResponseEntity.badRequest().body("OTP is invalid!");
	}
	
	public String generateOTP() {
        return new DecimalFormat("00000")
                .format(new Random().nextInt(99999));
    }
    public OtpResponseDto  SendMail(OtpRequest otpRequest ) {
		System.out.println("inside sendMail :: "+otpRequest.getBody());
        try{ 

            String subject =  "👋 Welcome  to Sahtech 🎉!";
            String body = otpRequest.getBody();
            sendSimpleEmail(otpRequest.getUsername(),subject,body);
			if (otpRequest.getOtp() != null) {
				otpMap.put(otpRequest.getUsername(), otpRequest.getOtp());
			}
		
			return new OtpResponseDto(OtpStatus.DELIVERED, "Email sent successfully");
		}
		catch (Exception e) {
			return new OtpResponseDto(OtpStatus.FAILED, e.getMessage());

        }
	}

	private void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
	


}}