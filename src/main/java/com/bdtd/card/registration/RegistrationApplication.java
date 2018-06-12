package com.bdtd.card.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"com.bdtd.card.registration", "com.stylefeng.guns"})
public class RegistrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}
	
	

}
