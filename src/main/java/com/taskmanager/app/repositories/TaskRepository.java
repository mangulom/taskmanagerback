package com.taskmanager.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.taskmanager.app.models.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
}