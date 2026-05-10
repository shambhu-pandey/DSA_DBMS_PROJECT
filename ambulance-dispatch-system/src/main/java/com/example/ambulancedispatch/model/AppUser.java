package com.example.ambulancedispatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
public class AppUser {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String fullName;
    private String role;
    private String assignedAmbulanceId;
    private LocalDateTime createdAt;

    public AppUser() {
        this.createdAt = LocalDateTime.now();
        this.role = "USER";
    }

    public AppUser(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = "USER";
        this.createdAt = LocalDateTime.now();
    }

    public AppUser(String username, String password, String fullName, String role, String assignedAmbulanceId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.assignedAmbulanceId = assignedAmbulanceId;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAssignedAmbulanceId() {
        return assignedAmbulanceId;
    }

    public void setAssignedAmbulanceId(String assignedAmbulanceId) {
        this.assignedAmbulanceId = assignedAmbulanceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
