package com.codingrecipe.member;

import com.codingrecipe.member.service.PasswordEncryptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
