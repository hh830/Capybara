package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.PatientDTO;
import com.codingrecipe.member.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/Patients")
public class PatientController {
    //생성자 주입
    private final PatientService patientService;

    //회원가입페이지 출력 요청
    @GetMapping("/users/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/users/save")
    public String save(@ModelAttribute PatientDTO patientDTO){
        patientService.save(patientDTO);
        return "save";
    }
}
