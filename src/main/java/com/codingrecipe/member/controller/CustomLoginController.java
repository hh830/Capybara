package com.codingrecipe.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomLoginController {
    @GetMapping("/customLoginPage")
    public ResponseEntity<String> customLoginPage() {
        // 로그인 페이지에 대한 커스텀 로직 수행
        // 로그인 성공 시 JSON 응답을 생성
        String jsonResponse = "{\"message\": \"Custom login page response\"}";
        return ResponseEntity.ok(jsonResponse);
    }
}
