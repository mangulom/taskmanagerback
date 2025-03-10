package com.taskmanager.app.services;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	public String secretKey;
	
	@Value("${jwt.expirationMs}")
    public long expirationTime;

    public String generateToken(String username) {
    	try {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Token inválido
        }
    }
}
