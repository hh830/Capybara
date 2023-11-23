package com.codingrecipe.member.controller;
import com.codingrecipe.member.dto.userDTO.RegistrationDTO;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

public class CustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO registrationDTO = (RegistrationDTO) target;

        if (registrationDTO.getUserId().trim().isEmpty()) {
            errors.rejectValue("userId", "error.userId", "입력된 아이디에 공백이 있어요");
        }

        if (registrationDTO.getUserName().trim().isEmpty()) {
            errors.rejectValue("userName", "error.userName", "입력된 이름에 공백이 있어요");
        }

        if (registrationDTO.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "error.password", "입력된 비밀번호에 공백이 있어요");
        }
        if (registrationDTO.getPhoneNumber().trim().isEmpty()) {
            errors.rejectValue("phoneNumber", "error.phoneNumber", "입력된 전화번호에 공백이 있어요");
        }
        if (registrationDTO.getBirthDate().trim().isEmpty()) {
            errors.rejectValue("birthDate", "error.birthDate", "입력된 생년월일에 공백이 있어요");
        }

    }
}
