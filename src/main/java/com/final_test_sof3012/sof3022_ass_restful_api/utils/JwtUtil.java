package com.final_test_sof3012.sof3022_ass_restful_api.utils;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtUtil {


    @Value("${EXPIRATION_JWT_TIME:84600000}")
    private static Long EXPIRATION_TIME;

    private final Key getSigningKey;

    public String generateToken(String username, Set<Roles> roles){
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",roles);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractedAllClaims(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(JwtException e){
            return null;
        }
    }
    public String extractUsername(String token){
       Claims claims = extractedAllClaims(token);
       return claims != null ? claims.getSubject() : null;
    }
    public String extractRoles(String token) {
        Claims claims = extractedAllClaims(token);
        return claims != null ? claims.get("roles", String.class) : null;
    }
    public boolean validateToken(String token,String username){
        String extractedUsername = extractUsername(token);
        return extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        }catch(JwtException e){
            return true;
        }
    }
}
