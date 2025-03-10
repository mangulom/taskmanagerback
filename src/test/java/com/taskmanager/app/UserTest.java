package com.taskmanager.app;

import org.junit.jupiter.api.Test;

import com.taskmanager.app.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testAllArgsConstructor() {
        User user = new User("1", "John Doe", "johndoe", "john@example.com", 
                              "password123", "USER", "2023-01-01", 
                              "2023-01-02", "ACTIVE", "NO");

        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe", user.getNick());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("2023-01-01", user.getCreated());
        assertEquals("2023-01-02", user.getUpdated());
        assertEquals("ACTIVE", user.getStatus());
        assertEquals("NO", user.getVerified());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();
        user.setId("1");
        user.setName("Jane Doe");
        user.setNick("janedoe");
        user.setEmail("jane@example.com");
        user.setPassword("password456");
        user.setRole("ADMIN");
        user.setCreated("2023-02-01");
        user.setUpdated("2023-02-02");
        user.setStatus("ACTIVE");
        user.setVerified("YES");

        assertEquals("1", user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("janedoe", user.getNick());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("password456", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertEquals("2023-02-01", user.getCreated());
        assertEquals("2023-02-02", user.getUpdated());
        assertEquals("ACTIVE", user.getStatus());
        assertEquals("YES", user.getVerified());
    }

    @Test
    void testToString() {
        User user = new User("1", "John Doe", "johndoe", "john@example.com", 
                              "password123", "USER", "2023-01-01", 
                              "2023-01-02", "ACTIVE", "NO");

        String expectedString = "User [id=1, name=John Doe, nick=johndoe, email=john@example.com, password=password123, role=USER, created=2023-01-01, updated=2023-01-02, status=ACTIVE, verified=NO]";
        assertEquals(expectedString, user.toString());
    }
}