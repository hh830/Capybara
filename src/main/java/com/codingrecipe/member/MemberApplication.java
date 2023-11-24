package com.codingrecipe.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}
	/*@Bean
	CommandLineRunner run(PasswordEncryptionService passwordEncryptionService) {
		return args -> {
			passwordEncryptionService.encryptExistingPasswords();
		};
	}*/

}
