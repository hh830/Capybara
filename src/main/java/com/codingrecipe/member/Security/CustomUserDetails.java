package com.codingrecipe.member.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String userId;
    private String password;
    private String username;
    private String phoneNumber; // 핸드폰 번호 추가

    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetails(String userId, String username, String password,
                             Collection<? extends GrantedAuthority> authorities,
                             boolean accountNonExpired, boolean accountNonLocked,
                             boolean credentialsNonExpired, boolean enabled) {
        this.userId = userId;
        this.password = password;
        this.username=username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    // Getters and setters for all fields
    // 예: getUsername, getPassword, getAuthorities, etc.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // UserDetails 인터페이스 구현
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
