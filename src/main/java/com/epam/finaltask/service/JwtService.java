package com.epam.finaltask.service;

import com.epam.finaltask.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwtSecret}")
    private String jwtSecret;

    public String generateToken(User expectedUser) {
        SecretKey key = getSigningKey();
        return Jwts.builder()
                .setSubject(expectedUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 днів
                .signWith(key)
                .compact();
    }

    private SecretKey getSigningKey() {
        return new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS512.getJcaName());
    }

    public String getUsernameFromJWT(String token) {
        SecretKey key = getSigningKey();
        Claims claims = Jwts.parserBuilder() // Використання нового методу
                .setSigningKey(key) // Передача об'єкта ключа
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateJWT(String token) {
        try {
            Jwts.parserBuilder() // Використання нового API
                    .setSigningKey(getSigningKey()) // Передача ключа
                    .build() // Побудова парсера
                    .parseClaimsJws(token); // Верифікація токена
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT");
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}
