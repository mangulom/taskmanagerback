package com.taskmanager.app;

import com.taskmanager.app.controllers.AuthController;
import com.taskmanager.app.models.User;
import com.taskmanager.app.response.Response;
import com.taskmanager.app.services.JwtUtil;
import com.taskmanager.app.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setNick("testUser");
        testUser.setPassword("password");
    }

    @Test
    public void register_ShouldReturnCreated_WhenTokenIsValid() {
        String token = "Bearer validToken";
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(userService.register(testUser)).thenReturn(testUser);

        ResponseEntity<Response> response = authController.register(token, testUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario creado satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void register_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = authController.register(token, testUser);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }

    @Test
    public void register_ShouldReturnUnauthorized_WhenTokenIsMissing() {
        ResponseEntity<Response> response = authController.register(null, testUser);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token no proporcionado o formato inválido", response.getBody().getMessage());
    }

    @Test
    public void listUsers_ShouldReturnOk_WhenTokenIsValid() {
        String token = "Bearer validToken";
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(userService.listUser()).thenReturn(List.of(testUser));

        ResponseEntity<Response> response = authController.listUsers(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lista de Usuarios obtenida satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void listUsers_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = authController.listUsers(token);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }

    @Test
    public void listUsers_ShouldReturnUnauthorized_WhenTokenIsMissing() {
        ResponseEntity<Response> response = authController.listUsers(null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token no proporcionado o formato inválido", response.getBody().getMessage());
    }

    @Test
    public void login_ShouldReturnOk_WhenCredentialsAreValid() {
        when(userService.findByNick("testUser")).thenReturn(testUser);
        when(jwtUtil.generateToken("testUser")).thenReturn("generatedToken");

        ResponseEntity<Response> response = authController.login(testUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario autenticado correctamente", response.getBody().getMessage());
    }

    @Test
    public void login_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.findByNick("unknownUser")).thenReturn(null);

        User unknownUser = new User();
        unknownUser.setNick("unknownUser");
        ResponseEntity<Response> response = authController.login(unknownUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario desconocido", response.getBody().getMessage());
    }

    @Test
    public void login_ShouldReturnUnauthorized_WhenInvalidCredentials() {
        when(userService.findByNick("testUser")).thenReturn(testUser);

        User invalidUser = new User();
        invalidUser.setNick("testUser");
        invalidUser.setPassword("wrongPassword");

        ResponseEntity<Response> response = authController.login(invalidUser);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales inválidas", response.getBody().getMessage());
    }

    @Test
    public void login_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        when(userService.findByNick("testUser")).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Response> response = authController.login(testUser);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Ocurrió un error en el servicio: Error"));
    }
}