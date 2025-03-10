package com.taskmanager.app.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status; // "POR HACER", "EN PROGRESO", "COMPLETADA"
}