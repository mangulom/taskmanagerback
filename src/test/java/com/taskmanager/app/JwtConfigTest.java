package com.taskmanager.app;

import org.junit.jupiter.api.Test;

import com.taskmanager.app.config.JwtConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtConfigTest {

    @Test
    public void testJwtConfigSettersAndGetters() {
        JwtConfig jwtConfig = new JwtConfig();

        jwtConfig.setSecret("mySecret");
        jwtConfig.setExpirationMs(3600000);

        assertEquals("mySecret", jwtConfig.getSecret());
        assertEquals(3600000, jwtConfig.getExpirationMs());
    }
}