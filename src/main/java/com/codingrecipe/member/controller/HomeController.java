package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    //회원가입페이지 출력 요청
    @GetMapping("/users/save")
    public String showSaveForm() {
        return "save";
    }

    @GetMapping("/users/login")
    public String showLoginForm() {
        return "login";
    }

}
