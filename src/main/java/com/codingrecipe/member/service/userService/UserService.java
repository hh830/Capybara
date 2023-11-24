package com.codingrecipe.member.service.userService;

import com.codingrecipe.member.dto.userDTO.UserDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.userRepository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PatientRepository patientRepository;

    public UserDTO getUserInfo(String username) {
        Optional<Patients> userOptional = patientRepository.findById(username);
        System.out.println();
        if (userOptional.isPresent()) {
            System.out.println("username = " + username);
            Patients patients = userOptional.get();
            // 필요한 정보를 가져와서 UserDTO에 설정
            UserDTO userDTO = new UserDTO(patients.getName(), patients.getPhoneNumber(), patients.getBirthDate());
            return userDTO;
        } else {
            System.out.println("11username = " + username);
            // 사용자 정보가 없는 경우에 대한 처리
            throw new CustomValidationException(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다");
        }
    }
}
