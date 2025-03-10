package com.taskmanager.app.services;

import org.springframework.stereotype.Service;
import com.taskmanager.app.models.Task;
import com.taskmanager.app.repositories.TaskRepository;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(String id, Task updatedTask) {
        return repository.findById(id).map(task -> {
            task.setStatus(updatedTask.getStatus());
            return repository.save(task);
        }).orElseThrow(() -> new RuntimeException("Tarea no Encontrada"));
    }

    public void deleteTask(String id) {
    	System.out.println("Id " + id);
        repository.deleteById(id);
    }
}

