package com.codingrecipe.member.service;


import com.codingrecipe.member.dto.LoginDTO;
import com.codingrecipe.member.dto.RegistrationDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자 주입으로 BCryptPasswordEncoder 인스턴스를 받음
    public LoginService(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public LoginDTO login(String username, String password) {
        Patients patients = patientRepository.findByPatientId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (bCryptPasswordEncoder.matches(password, patients.getPassword())) {
            return new LoginDTO(patients);
        } else {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }
}
