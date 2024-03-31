package com.nizar.SahTech.users.Auth.Otp;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nizar.SahTech.security.TwilioConfig;
import com.nizar.SahTech.users.Auth.UserEntity;
import com.nizar.SahTech.users.Auth.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class SmsService {

	private final TwilioConfig twilioConfig;
	private final UserRepository userRepository;
    Map<String, String> otpMap = new HashMap<>();



	public OtpResponseDto sendSMS(OtpRequest otpRequest) {
		OtpResponseDto otpResponseDto = null;
		try {
			String User = otpRequest.getUsername();
			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
			String otp = generateOTP();
			String otpMessage = "أهلا بك في Sah'tech , رقم التأكيد الخاص بك هو   " + otp + " ";
			Message message = Message
			        .creator(to, from,
			                otpMessage)
			        .create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponseDto;
	}
	
	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys = otpMap.keySet();
		String username = null;
		for(String key : keys)
			username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username,otpValidationRequest.getOtpNumber());
		try {
UserEntity	user = userRepository.findByUsername(username).get();
			user.setActive(true);
			userRepository.save(user);
						
		} catch (Exception e) {
			e.printStackTrace();
		}

            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
	}
	
	private String generateOTP() {
        return new DecimalFormat("00000")
                .format(new Random().nextInt(99999));
    }

}