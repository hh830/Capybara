package com.codingrecipe.member.controller;

import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.codingrecipe.member.dto.RegistrationDTO;
import com.codingrecipe.member.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/Patients")
public class RegistrationController {
    //생성자 주입
    private final RegistrationService registrationService;

    @PostMapping("/users/save")
    public ResponseEntity<?> savePatient(@RequestBody @Valid RegistrationDTO registrationDTO) {
        registrationService.save(registrationDTO);
        // 여기서 patientId는 patientDTO에서 얻어야 합니다.
        // 예를 들어, patientService.save 메서드가 저장된 엔티티 또는 DTO를 반환하도록 수정할 수 있습니다.


        return ResponseEntity.ok(registrationDTO);
    }



}

