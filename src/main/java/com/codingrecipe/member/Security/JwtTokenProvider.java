
package com.codingrecipe.member.Security;

import com.codingrecipe.member.exception.CustomValidationException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

// JWT 토큰 생성 및 검증을 담당하는 클래스
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey;
    private long validityInMilliseconds;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}")long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    public UserDetails getUserDetails(String token) {
        String username = getUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 이제 userDetails 객체에는 필요한 모든 정보(username, password, authorities)가 포함되어 있습니다.
        return userDetails;
    }

    private String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }



    private String getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        // 토큰의 유효성만 확인
        try {
            System.out.println("validate token = " + token);;
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("request = " + request);
        System.out.println("bearerToken = " + bearerToken);
        //System.out.println(bearerToken.startsWith("Bearer "));
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println("resolveToken = true");
            return bearerToken.substring(7); // "Bearer " 이후의 문자열을 반환
        }
        return null;
    }
}