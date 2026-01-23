package com.onlinelearning.userservice.controller;

import com.onlinelearning.userservice.dto.*;
import com.onlinelearning.userservice.entity.User;
import com.onlinelearning.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            System.out.println("Registration request: " + request.getUsername() + ", " + request.getEmail() + ", " + request.getRole());
            User user = userService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
            );
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            System.out.println("Registration error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("Login request: " + request.getEmail());
            String token = userService.authenticateUser(request.getEmail(), request.getPassword());
            Optional<User> userOpt = userService.findByEmail(request.getEmail());
            User user = userOpt.get();
            AuthResponse response = new AuthResponse(token, user.getUsername(), user.getRole());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("Login error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}