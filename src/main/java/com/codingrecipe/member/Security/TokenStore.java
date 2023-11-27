package com.codingrecipe.member.Security;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String username, String token) {
        tokenStore.put(username, token);
    }

    public void removeToken(String username) { //기존 토큰 삭제
        tokenStore.remove(username);
    }
    public boolean isTokenRemoved(String username) {
        return !tokenStore.containsKey(username);
    }

    public String getToken(String username) {
        return tokenStore.get(username);
    }
}
