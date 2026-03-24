package com.learns.blogapi.service;

import com.learns.blogapi.model.User;
import com.learns.blogapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Layered architecture in Spring:
    // - Controller: handles HTTP requests/responses (web layer)
    // - Service: contains business logic, decides what to do with data
    // - Repository: handles database operations (data access layer)
    //
    // Each layer is injected into the one above it:
    // - Controller injects Service → delegates tasks
    // - Service injects Repository → performs DB operations
    //
    // Benefits:
    // - Separation of concerns, reusability, testability, and maintainability
    private final UserRepository userRepository ;

    // Constructor injection: UserRepository is provided when this service is created
    // - final ensures it can't be changed later
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository ;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll() ;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id) ;
    }

    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail()) ;
        }
        return userRepository.save(user) ;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
