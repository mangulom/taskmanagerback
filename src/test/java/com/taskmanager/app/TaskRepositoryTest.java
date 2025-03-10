package com.taskmanager.app;

import com.taskmanager.app.models.Task;
import com.taskmanager.app.repositories.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTitle("Tarea Prueba");
        task.setDescription("Esta es una tarea de prueba.");
        task = taskRepository.save(task);
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertTrue(foundTask.isPresent());
        assertEquals("Tarea Prueba", foundTask.get().getTitle());
    }

    @Test
    void deleteById_ShouldRemoveTask_WhenTaskExists() {
        taskRepository.deleteById(task.getId());
        Optional<Task> foundTask = taskRepository.findById(task.getId());
        assertFalse(foundTask.isPresent());
    }

    @Test
    void save_ShouldPersistTask_WhenTaskIsNew() {
        Task newTask = new Task();
        newTask.setTitle("Nueva tarea");
        newTask.setDescription("Esta es una tarea de prueba.");
        Task savedTask = taskRepository.save(newTask);
        assertNotNull(savedTask.getId());
    }
}