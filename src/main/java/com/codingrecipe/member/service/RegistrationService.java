package com.codingrecipe.member.service;

import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.exception.InvalidRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.codingrecipe.member.dto.RegistrationDTO;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RegistrationService {
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자 주입으로 BCryptPasswordEncoder 인스턴스를 받음
    public RegistrationService(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public RegistrationDTO save(RegistrationDTO registrationDTO) {

        validatePatientDTO(registrationDTO);

        // 비밀번호를 BCryptPasswordEncoder로 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(registrationDTO.getPassword());
        registrationDTO.setPassword(encodedPassword);

        // 데이터베이스에 저장하는 로직
        Patients savedPatient = patientRepository.save(new Patients(registrationDTO));

        return new RegistrationDTO(savedPatient);
    }

    private void validatePatientDTO(RegistrationDTO registrationDTO) {
        // 유효성 검사 로직
        if (registrationDTO.getUserId() == null || registrationDTO.getUserId().isEmpty()) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "아이디가 비어있어요");
        }
        else if(registrationDTO.getPassword() == null || registrationDTO.getPassword().isEmpty())
        {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "비밀번호가 비어있어요");
        }
        else if(registrationDTO.getUserName() == null || registrationDTO.getUserName().isEmpty())
        {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "이름이 비어있어요");

        }
        else if(registrationDTO.getBirthDate()== null || registrationDTO.getBirthDate().isEmpty())
        {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "생년월일이 비어있어요");

        }
        else if(registrationDTO.getPhoneNumber() == null || registrationDTO.getPhoneNumber().isEmpty())
        {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "전화번호가 비어있어요");
        }
        else{

            // 이미 아이디가 존재하는지 확인
            Optional<Patients> existingPatient = patientRepository.findByPatientId(registrationDTO.getUserId());
            if (existingPatient.isPresent()) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 아이디");
            }

            // 비밀번호 길이가 8자 미만인 경우
            if (registrationDTO.getPassword().length() < 8) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "비밀번호 8자 이상 필수 입력");
            }


            if (!Pattern.matches("^[가-힣]+$", registrationDTO.getUserName())) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "이름 형식 오류 (한글만 포함)");
            }

            // 생년월일 형식 검사
            try {
                LocalDate.parse(registrationDTO.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "생일 형식 오류 (yyyy-MM-dd)");
            }

            // 전화번호 형식 검사
            if (!Pattern.matches("\\d{3}-\\d{4}-\\d{4}", registrationDTO.getPhoneNumber())) {
                throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "전화번호 형식 오류 (000-0000-0000)");
            }
        }
    }
}
