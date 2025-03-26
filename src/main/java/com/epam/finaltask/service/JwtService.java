package com.epam.finaltask.service;

import com.epam.finaltask.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpiration}")
    private Integer jwtExpiration;

    public String generateToken(User expectedUser) {
        SecretKey key = getSigningKey();
        return Jwts.builder()
                .setSubject(expectedUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
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
            log.error("Invalid JWT", e);
            return false;
        }
    }
}
