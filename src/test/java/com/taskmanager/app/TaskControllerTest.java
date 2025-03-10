package com.taskmanager.app;

import com.taskmanager.app.controllers.TaskController;
import com.taskmanager.app.models.Task;
import com.taskmanager.app.response.Response;
import com.taskmanager.app.services.JwtUtil;
import com.taskmanager.app.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllTasks_ShouldReturnOk_WhenTokenIsValid() {
        String token = "Bearer validToken";
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(new Task()));

        ResponseEntity<Response> response = taskController.getAllTasks(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lista de Tareas obtenida satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void getAllTasks_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = taskController.getAllTasks(token);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }

    @Test
    public void getAllTasks_ShouldReturnUnauthorized_WhenTokenIsMissing() {
        ResponseEntity<Response> response = taskController.getAllTasks(null);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token no proporcionado o formato inválido", response.getBody().getMessage());
    }

    @Test
    public void createTask_ShouldReturnCreated_WhenTokenIsValid() {
        String token = "Bearer validToken";
        Task task = new Task();
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(taskService.createTask(task)).thenReturn(task);

        ResponseEntity<Response> response = taskController.createTask(token, task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tarea creada satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void createTask_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        Task task = new Task();
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = taskController.createTask(token, task);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }

    @Test
    public void updateTask_ShouldReturnOk_WhenTokenIsValid() {
        String token = "Bearer validToken";
        String taskId = "1";
        Task task = new Task();
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(taskService.updateTask(taskId, task)).thenReturn(task);

        ResponseEntity<Response> response = taskController.updateTask(token, taskId, task);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tarea actualizada satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void updateTask_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        String taskId = "1";
        Task task = new Task();
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = taskController.updateTask(token, taskId, task);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }

    @Test
    public void deleteTask_ShouldReturnNoContent_WhenTokenIsValid() {
        String token = "Bearer validToken";
        String taskId = "1";
        when(jwtUtil.validateToken("validToken")).thenReturn(true);

        ResponseEntity<Response> response = taskController.deleteTask(token, taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Tarea eliminada satisfactoriamente", response.getBody().getMessage());
    }

    @Test
    public void deleteTask_ShouldReturnUnauthorized_WhenTokenIsInvalid() {
        String token = "Bearer invalidToken";
        String taskId = "1";
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        ResponseEntity<Response> response = taskController.deleteTask(token, taskId);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token inválido o ha expirado", response.getBody().getMessage());
    }
}