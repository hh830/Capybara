package com.codingrecipe.member.Security;

import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtTokenFilter extends GenericFilterBean {
//모든 요청에 대해 실행되어 Authorization 헤더를 확인하고 토큰을 추출
    private JwtTokenProvider jwtTokenProvider;
    private TokenStore tokenStore;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, TokenStore tokenStore) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenStore = tokenStore;
    }
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            // 토큰 검사를 생략할 경로 목록 정의
            String[] skipPaths = {"/users/save", "/users/duplication/**", "/users/login", "/hospitals", "/hospitals/top","/reservations/available-times/**"};

            // 현재 요청 경로 확인
            String requestPath = request.getRequestURI();

            // 토큰 검사를 생략할 경로인지 확인
            boolean skipTokenCheck = Arrays.stream(skipPaths).anyMatch(path -> antPathMatcher.match(path, requestPath));
            if (!skipTokenCheck) {
                // 토큰 검사 로직
                String token = jwtTokenProvider.resolveToken(request);
                if (token != null && jwtTokenProvider.validateToken(token)) {
                    String username = jwtTokenProvider.getUsername(token);
                    System.out.println("username = " + username);
                    String storedToken = tokenStore.getToken(username);
                    System.out.println("storedToken = " + storedToken);
                    if (storedToken != null && storedToken.equals(token)) {
                        Authentication auth = jwtTokenProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        filterChain.doFilter(req, res);
                    } else {
                        // 토큰이 저장소에 없거나 일치하지 않는 경우, 인증 실패 처리
                        if (antPathMatcher.match("/hospitals/details/**", requestPath)) {
                            // "/hospitals/details" 경로에 대해서는 요청을 계속 진행
                            filterChain.doFilter(req, res);
                            return;
                        } else {
                            HttpServletResponse response = (HttpServletResponse) res;

                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            String jsonErrorResponse = "{\"status\": 401, \"message\": \"유효하지 않은 토큰\"}";
                            System.out.println("1111111");
                            response.getWriter().write(jsonErrorResponse);
                        }
                    }
                } else {
                    // 토큰이 유효하지 않은 경우, 인증 실패 처리
                    filterChain.doFilter(req, res);
                }
            } else {
                // 토큰 검사를 생략하고, 필터 체인 계속 진행
                filterChain.doFilter(req, res);
                return;
            }
        }catch (CustomValidationException e) {
            // 클라이언트에게 오류 메시지 반환
            HttpServletResponse response = (HttpServletResponse) res;
            //filterChain.doFilter(req, res); // 토큰이 없거나 유효하지 않으면 필터 체인 계속 진행

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonErrorResponse = "{\"status\": 401, \"message\": \"유효하지 않은 토큰\"}";
            System.out.println("111111111112222222");

            response.getWriter().write(jsonErrorResponse);
            filterChain.doFilter(req, res); // 토큰이 없거나 유효하지 않으면 필터 체인 계속 진행

            // 요청을 더 이상 진행시키지 않고 종료
            return;
        }
    }



}
