package com.codingrecipe.member.Security;

import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
//모든 요청에 대해 실행되어 Authorization 헤더를 확인하고 토큰을 추출
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            /*else {
                // 토큰이 없거나 유효하지 않은 경우 401 상태 코드 설정
                HttpServletResponse response = (HttpServletResponse) res;
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String jsonErrorResponse = "{\"status\": 401, \"error\": \"유효하지 않은 토큰입니다.\"}";
                response.getWriter().write(jsonErrorResponse);
                return;
            }*/
        } catch (CustomValidationException e) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonErrorResponse = "{\"status\": 401, \"error\": \"유효하지 않은 토큰\"}";
            response.getWriter().write(jsonErrorResponse);
            return;
        }
        filterChain.doFilter(req, res);
    }


}
