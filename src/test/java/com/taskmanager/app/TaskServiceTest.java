package com.taskmanager.app;

import com.taskmanager.app.models.Task;
import com.taskmanager.app.repositories.TaskRepository;
import com.taskmanager.app.services.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId("1");
        task.setTitle("Tarea de prueba");
        task.setDescription("Esta es una tarea de prueba.");
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Tarea de prueba", tasks.get(0).getTitle());
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.createTask(task);

        assertEquals("Tarea de prueba", savedTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        Task updatedTask = new Task();
        updatedTask.setStatus("COMPLETADA");

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.updateTask(task.getId(), updatedTask);

        assertEquals("COMPLETADA", result.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        Task updatedTask = new Task();
        updatedTask.setStatus("COMPLETADA");

        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(task.getId(), updatedTask);
        });

        assertEquals("Tarea no Encontrada", exception.getMessage());
    }

    @Test
    void deleteTask_ShouldCallDeleteById() {
        taskService.deleteTask(task.getId());
        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}