package com.codingrecipe.member;

import com.codingrecipe.member.controller.CustomValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class AppConfig {
    @Bean
    public Validator validator() {
        return new CustomValidator();
    }
}
