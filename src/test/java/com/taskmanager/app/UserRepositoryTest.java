package com.taskmanager.app;

import com.taskmanager.app.models.User;
import com.taskmanager.app.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setNick("testUser");
        userRepository.save(user);
    }

    @Test
    void findByNick_ShouldReturnUser_WhenUserExists() {
        User foundUser = userRepository.findByNick("testUser");
        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getNick());
    }

    @Test
    void findByNick_ShouldReturnNull_WhenUserDoesNotExist() {
        User foundUser = userRepository.findByNick("nonExistingUser");
        assertNull(foundUser);
    }

    @Test
    void existsByNick_ShouldReturnTrue_WhenUserExists() {
        boolean exists = userRepository.existsByNick("testUser");
        assertTrue(exists);
    }

    @Test
    void existsByNick_ShouldReturnFalse_WhenUserDoesNotExist() {
        boolean exists = userRepository.existsByNick("nonExistingUser");
        assertFalse(exists);
    }
}