package com.killerchess.core.services;

import com.killerchess.core.repositories.UserRepository;
import com.killerchess.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User entity) {
        userRepository.save(entity);
        return entity;
    }

    public User find(User entity) {
        return userRepository.findByLogin(entity.getLogin());
    }

    public boolean existsUser(User entity) {
        return userRepository.existsById(entity.getLogin());
    }
}
