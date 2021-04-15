package com.cbsys.saleexplore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring boot configuration,  main function here
 * The security is configured by following https://www.callicoder.com/spring-boot-security-oauth2-social-login-part-2/
 */
@SpringBootApplication()
@EnableScheduling
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}