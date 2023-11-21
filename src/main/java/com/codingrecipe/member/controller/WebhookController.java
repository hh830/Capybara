package com.codingrecipe.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String payload) {
        // GitHub webhook의 payload를 처리하는 로직 구현
        System.out.println("Received webhook with payload: " + payload);

        // 배포 스크립트 실행 로직
        // executeDeploymentScript();

        return "Webhook processed";
    }

    // 배포 스크립트 실행 메소드
    private void executeDeploymentScript() {
        try {
            ProcessBuilder builder = new ProcessBuilder("/path/to/deployment/script.sh");
            builder.inheritIO(); // 스크립트의 출력을 콘솔로 전달
            Process process = builder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
