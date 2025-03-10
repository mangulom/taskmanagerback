package com.taskmanager.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.taskmanager.app.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByNick(String nick);
    boolean existsByNick(String nick);
}