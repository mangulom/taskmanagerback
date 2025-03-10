package com.taskmanager.app;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.taskmanager.app.services.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil.secretKey = "mySecret";
        jwtUtil.expirationTime = 3600000; 
    }

    @Test
    public void generateToken_ShouldReturnToken_WhenUsernameIsValid() {
        String token = jwtUtil.generateToken("testUser");
        assertNotNull(token);
        assertTrue(token.startsWith("ey"));
    }

    @Test
    public void extractUsername_ShouldReturnUsername_WhenTokenIsValid() {
        String token = jwtUtil.generateToken("testUser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    public void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        String token = jwtUtil.generateToken("testUser");
        boolean isValid = jwtUtil.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    public void validateToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        String invalidToken = "invalidToken";
        boolean isValid = jwtUtil.validateToken(invalidToken);
        assertFalse(isValid);
    }
}