package com.codingrecipe.member.controller.userController;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.codingrecipe.member.dto.userDTO.RegistrationDTO;
import com.codingrecipe.member.service.userService.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.Validator; // 올바른 Validator 임포트
import java.util.HashMap;
import java.util.Map;

//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/Patients")
public class RegistrationController {
    //생성자 주입
    private final RegistrationService registrationService;
    private final Validator validator; // Validator 주입

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @PostMapping("/users/save")
    public ResponseEntity<?> savePatient(@RequestBody @Valid RegistrationDTO registrationDTO, BindingResult result) {
        Map<String, Object> responseBody = new HashMap<>();
/*
        if(result.hasErrors()) {

        // 여기서 patientId는 patientDTO에서 얻어야 합니다.
        // 예를 들어, patientService.save 메서드가 저장된 엔티티 또는 DTO를 반환하도록 수정할 수 있습니다.

            responseBody.put("status", HttpStatus.BAD_REQUEST.value());

            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            //responseBody.put("errors", errors);
            responseBody.put("message", error.getDefaultMessage());

            return ResponseEntity.badRequest().body(responseBody);

        }*/

        registrationService.save(registrationDTO);

        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "회원가입 성공");
        responseBody.put("userName", registrationDTO.getUserName());


        return ResponseEntity.ok(responseBody);

    }

}

