package com.bdtd.card.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages= {"com.bdtd.card.registration", "com.stylefeng.guns"})
@EnableTransactionManagement
@EnableCaching
@ServletComponentScan
public class RegistrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}
	
	

}

