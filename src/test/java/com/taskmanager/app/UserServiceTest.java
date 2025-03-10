package com.taskmanager.app;

import com.taskmanager.app.models.User;
import com.taskmanager.app.repositories.UserRepository;
import com.taskmanager.app.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("1");
        user.setNick("testUser");
    }

    @Test
    void register_ShouldReturnSavedUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.register(user);

        assertEquals("testUser", savedUser.getNick());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findByNick_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByNick("testUser")).thenReturn(user);

        User foundUser = userService.findByNick("testUser");

        assertNotNull(foundUser);
        assertEquals("testUser", foundUser.getNick());
        verify(userRepository, times(1)).findByNick("testUser");
    }

    @Test
    void findByNick_ShouldReturnNull_WhenUserDoesNotExist() {
        when(userRepository.findByNick("nonExistingUser")).thenReturn(null);

        User foundUser = userService.findByNick("nonExistingUser");

        assertNull(foundUser);
    }

    @Test
    void listUser_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = userService.listUser();

        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getNick());
    }
}