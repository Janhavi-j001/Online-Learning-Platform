package com.onlinelearning.userservice.dto;

import com.onlinelearning.userservice.entity.User;

public class AuthResponse {
    private String token;
    private String username;
    private User.Role role;
    
    public AuthResponse(String token, String username, User.Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }
    
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public User.Role getRole() { return role; }
}