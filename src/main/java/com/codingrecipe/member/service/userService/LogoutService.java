package com.codingrecipe.member.service.userService;
/*
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    @Autowired
    private TokenStore tokenStore;

    public void logout(String username) {
        // 사용자의 토큰을 TokenStore에서 제거
        tokenStore.removeToken(username);
    }
}

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String username = ...; // 현재 인증된 사용자의 이름을 추출
        authenticationService.logout(username);

        return ResponseEntity.ok().build();
    }
}

*/