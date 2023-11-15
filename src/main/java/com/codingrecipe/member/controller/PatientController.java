package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.PatientDTO;
import com.codingrecipe.member.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

//import static jdk.internal.logger.DefaultLoggerFinder.SharedLoggers.system;

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

    @GetMapping("/users/login")
    public String saveForm1() {
        return "login";
    }
    @PostMapping("/users/save")
    public String save(@Valid @ModelAttribute PatientDTO patientDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 오류 메시지를 모델에 추가
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "save";
        }
        patientService.save(patientDTO);
        // 성공적으로 처리된 후 리디렉트하거나 다른 페이지로 이동할 수 있습니다.
        return "login";
    }

    @PostMapping("/users/login")
    public String login(@ModelAttribute PatientDTO patientDTO, HttpSession session)
    {
        PatientDTO loginResult = patientService.login(patientDTO);
        if(loginResult!=null){
            System.out.println("로그인성공");
            //login 성공
            session.setAttribute("loginId", loginResult.getUserId());
            session.setAttribute("message", "로그인 성공");
            System.out.println("로그인성공22");

            return "main";
        } else{
            //login 실패
            return "login";
        }
    }
}
