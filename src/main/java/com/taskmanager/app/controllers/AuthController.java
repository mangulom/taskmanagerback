package com.taskmanager.app.controllers;

import org.springframework.web.bind.annotation.*;
import com.taskmanager.app.models.User;
import com.taskmanager.app.response.Response;
import com.taskmanager.app.services.JwtUtil;
import com.taskmanager.app.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token, @RequestBody User user) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7); // Quitar "Bearer "
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Usuario creado satisfactoriamente");
                response.setResult(userService.register(user));
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
    
    @GetMapping("/list")
    public ResponseEntity<Response> listUsers(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String token) {
        Response response = new Response();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                response.setMessage("Token no proporcionado o formato inválido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            String jwtToken = token.substring(7); // Quitar "Bearer "
            Boolean statusJwt = jwtUtil.validateToken(jwtToken);

            if (statusJwt) {
                response.setMessage("Lista de Usuarios obtenida satisfactoriamente");
                response.setResult(userService.listUser());
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

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        Response response = new Response();
        try {
            User foundUser = userService.findByNick(user.getNick());
            if (foundUser == null) {
                response.setMessage("Usuario desconocido");
                response.setResult(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                if (foundUser.getPassword().equals(user.getPassword())) {
                    response.setMessage("Usuario autenticado correctamente");
                    response.setResult(jwtUtil.generateToken(user.getNick()));
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setMessage("Credenciales inválidas");
                    response.setResult(null);
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            }
        } catch (Exception e) {
            response.setMessage("Ocurrió un error en el servicio: " + e.getMessage());
            response.setResult(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
