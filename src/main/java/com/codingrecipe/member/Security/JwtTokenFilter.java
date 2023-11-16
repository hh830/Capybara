package com.codingrecipe.member.Security;

import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {
//모든 요청에 대해 실행되어 Authorization 헤더를 확인하고 토큰을 추출
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
/*
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch(Exception e){
            logger.error("JWT Token validation error", e);

            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "Invalid token: " + e.getMessage());
        }
        filterChain.doFilter(req, res);
    }*/
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
        } catch (CustomValidationException e) {
            // Instead of throwing the exception, you can log it and set an error attribute.
            HttpServletRequest request = (HttpServletRequest) req;
            request.setAttribute("jwtTokenValidationFailed", true);
            // Log the exception with your logger here
        }
        filterChain.doFilter(req, res);
    }


}
