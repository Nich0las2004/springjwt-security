package com.myapp.SpringJWT_Security.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist {
    private static final Set<String> BLACKLIST = new HashSet<>();

    public static void addToken(String token){
        BLACKLIST.add(token);
    }

    public static boolean isTokenBlacklisted(String token){
        return BLACKLIST.contains(token);
    }
}
