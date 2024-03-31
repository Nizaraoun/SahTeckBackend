package com.nizar.SahTech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.nizar.SahTech.security.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;

@EnableConfigurationProperties

@SpringBootApplication
public class SahTechApplication {


	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}


	public static void main(String[] args) {
		SpringApplication.run(SahTechApplication.class, args);



	}
	

}
