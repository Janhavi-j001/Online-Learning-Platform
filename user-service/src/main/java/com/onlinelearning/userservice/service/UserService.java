package com.onlinelearning.userservice.service;

import com.onlinelearning.userservice.entity.User;
import com.onlinelearning.userservice.repository.UserRepository;
import com.onlinelearning.userservice.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public User registerUser(String username, String email, String password, User.Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User(username, email, passwordEncoder.encode(password), role);
        return userRepository.save(user);
    }
    
    public String authenticateUser(String email, String password) {
        System.out.println("Login attempt for email: " + email);
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            System.out.println("User not found: " + email);
            throw new RuntimeException("User not found");
        }
        
        User user = userOpt.get();
        System.out.println("User found, checking password...");
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Password mismatch!");
            throw new RuntimeException("Invalid password");
        }
        
        System.out.println("Login successful for: " + email);
        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}