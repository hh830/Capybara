package com.codingrecipe.member.controller.userController;

import com.codingrecipe.member.exception.CustomValidationException;
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
@RequestMapping("/users")
public class RegistrationController {
    //생성자 주입
    private final RegistrationService registrationService;
    private final Validator validator; // Validator 주입

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePatient(@RequestBody @Valid RegistrationDTO registrationDTO, BindingResult result) {
        Map<String, Object> responseBody = new HashMap<>();

        registrationService.save(registrationDTO);

        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "회원가입 성공");
        responseBody.put("userName", registrationDTO.getUserName());

        return ResponseEntity.ok(responseBody);

    }

    @GetMapping("/duplication")
    public ResponseEntity<?> checkDuplication(@RequestParam String userId){
        try{
            Map<String, Object> response = new HashMap<>();

            boolean isAvailable = registrationService.isUsernameAvailable(userId);

            return ResponseEntity.ok().body(Map.of("isAvailable", isAvailable));

        } catch (CustomValidationException e){
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 형식");
        }
        catch (Exception e){
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "중복 검사 오류");
        }

    }

}

