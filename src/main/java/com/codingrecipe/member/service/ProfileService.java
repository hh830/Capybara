package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.LoginDTO;
import com.codingrecipe.member.dto.ProfileDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class ProfileService {
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public Patients updateProfile(String username, ProfileDTO profileDTO) {
        try {
            // 사용자 정보 조회
            Patients patients = patientRepository.findById(username)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 비밀번호 변경 (null이 아닌 경우에만)
            if (profileDTO.getPassword() != null) {
                patients.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
            }

            // 이름 변경 (null이 아닌 경우에만)
            if (profileDTO.getUserName() != null) {
                patients.setName(profileDTO.getUserName());
            }

            // 전화번호 변경 (null이 아닌 경우에만)
            if (profileDTO.getPhoneNumber() != null) {
                patients.setPhoneNumber(profileDTO.getPhoneNumber());
            }

            //patientRepository.save(patients);
            return patientRepository.save(patients);
        } catch (DataAccessException e) {
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류");
        } catch (EntityNotFoundException e) {
            throw new CustomValidationException(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다.1");
        }
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        Patients patients = patientRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(oldPassword, patients.getPassword())) {
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");
        }

        patients.setPassword(passwordEncoder.encode(newPassword));
        patientRepository.save(patients);
    }
}
