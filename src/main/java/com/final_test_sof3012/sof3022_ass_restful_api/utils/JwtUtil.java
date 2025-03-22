package com.final_test_sof3012.sof3022_ass_restful_api.utils;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${EXPIRATION_JWT_TIME:84600000}")
    private Long EXPIRATION_TIME;
    @Value("${EXPIRATION_JWT_REFRESH_TIME:84600000}")
    private Long EXPIRATION_REFRESH_TIME;

    private  final Key getSigningKey;

    public String generateToken(String username,List<String> roles) {
        return Jwts
                .builder()
                .setSubject(username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TIME))
                .signWith(getSigningKey,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractedAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("LỖI: Không thể parse token! " + e.getMessage());
            return null;
        }
    }


    public String extractUsername(String token) {
        Claims claims = extractedAllClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = extractedAllClaims(token);
        return claims != null ? (List<String>) claims.get("roles") : Collections.emptyList();
    }

    public boolean validateToken(String token, String username) {
        if (token == null || extractedAllClaims(token) == null) return false;
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractedAllClaims(token);
            if (claims == null) {
                return true;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            System.out.println("LỖI: Token không hợp lệ! " + e.getMessage());
            return true;
        }
    }

    public Long getExpirationTime(){
        return EXPIRATION_TIME / 1000;
    }
    public Long getExpirationRefreshTime(){
        return EXPIRATION_REFRESH_TIME/1000;
    }
}
