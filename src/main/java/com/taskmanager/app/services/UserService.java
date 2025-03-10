package com.taskmanager.app.services;


import org.springframework.stereotype.Service;

import com.taskmanager.app.models.User;
import com.taskmanager.app.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User register(User user) {
        return repository.save(user);
    }

    public User findByNick(String nick) {
        return repository.findByNick(nick);
    }
    
    public List<User> listUser() {
    	return repository.findAll();
    }
}
