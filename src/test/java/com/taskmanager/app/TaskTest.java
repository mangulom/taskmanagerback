package com.taskmanager.app;

import org.junit.jupiter.api.Test;

import com.taskmanager.app.models.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void testAllArgsConstructor() {
        Task task = new Task("1", "Tarea de prueba", "Esta es una tarea de prueba.", "POR HACER");

        assertEquals("1", task.getId());
        assertEquals("Tarea de prueba", task.getTitle());
        assertEquals("Esta es una tarea de prueba.", task.getDescription());
        assertEquals("POR HACER", task.getStatus());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Task task = new Task();
        task.setId("1");
        task.setTitle("Tarea de prueba");
        task.setDescription("Esta es una tarea de prueba.");
        task.setStatus("EN PROGRESO");

        assertEquals("1", task.getId());
        assertEquals("Tarea de prueba", task.getTitle());
        assertEquals("Esta es una tarea de prueba.", task.getDescription());
        assertEquals("EN PROGRESO", task.getStatus());
    }
}