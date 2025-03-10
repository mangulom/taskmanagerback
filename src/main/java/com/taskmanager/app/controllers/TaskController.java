package com.taskmanager.app.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanager.app.services.JwtUtil;
import com.taskmanager.app.models.Task;
import com.taskmanager.app.response.Response;
import com.taskmanager.app.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@Tag(name = "Task Controller", description = "API para la gestión de tareas")
public class TaskController {
    private final TaskService taskService;
    
    private final JwtUtil jwtUtil;
    
    public TaskController(TaskService taskService, JwtUtil jwtUtil) {
        this.taskService = taskService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las tareas", description = "Devuelve una lista de todas las tareas disponibles.")
    public ResponseEntity<Response> getAllTasks(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7);
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Lista de Tareas obtenida satisfactoriamente");
                response.setResult(taskService.getAllTasks());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Token inválido o ha expirado");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setMessage("Ocurrió un error en el servicio: " + e.getMessage());
            response.setResult(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    @PostMapping
    @Operation(summary = "Crear una tarea", description = "Crea una nueva tarea en la base de datos.")
    public ResponseEntity<Response> createTask(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token, @RequestBody Task task) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7);
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Tarea creada satisfactoriamente");
                response.setResult(taskService.createTask(task));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage("Token inválido o ha expirado");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setMessage("Ocurrió un error en el servicio: " + e.getMessage());
            response.setResult(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar el status de la tarea", description = "Actualiza el estado de una tarea existente mediante su ID.")
    public ResponseEntity<Response> updateTask(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token, @PathVariable String id, @RequestBody Task task) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7);
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Tarea actualizada satisfactoriamente");
                response.setResult(taskService.updateTask(id, task));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMessage("Token inválido o ha expirado");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setMessage("Ocurrió un error en el servicio: " + e.getMessage());
            response.setResult(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea", description = "Elimina una tarea de la base de datos mediante su ID.")
    public ResponseEntity<Response> deleteTask(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token,@PathVariable String id) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7);
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Tarea eliminada satisfactoriamente");
                taskService.deleteTask(id);
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            } else {
                response.setMessage("Token inválido o ha expirado");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            response.setMessage("Ocurrió un error en el servicio: " + e.getMessage());
            response.setResult(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }   
    }
}
