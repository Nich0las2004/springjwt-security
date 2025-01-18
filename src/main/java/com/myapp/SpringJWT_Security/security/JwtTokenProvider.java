package com.myapp.SpringJWT_Security.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;
    private static final long REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    public static String generateToken(String username, List<String> roles){
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String generateRefreshToken(String username, List<String> roles){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public static List<String> extractRoles(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles", List.class);
    }

    public static boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
