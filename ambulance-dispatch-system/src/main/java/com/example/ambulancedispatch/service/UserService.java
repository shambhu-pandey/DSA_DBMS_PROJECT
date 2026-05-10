package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.AppUser;
import com.example.ambulancedispatch.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements CommandLineRunner {
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin12345";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        seedUser(DEMO_USERNAME, DEMO_PASSWORD, "Admin", "ADMIN", null);
        seedUser("driver1", "driver12345", "Driver A1", "DRIVER", "A1");
        seedUser("user1", "user12345", "Emergency User", "USER", null);
    }

    public boolean authenticate(String username, String password) {
        return findAuthenticatedUser(username, password).isPresent();
    }

    public Optional<AppUser> findAuthenticatedUser(String username, String password) {
        String cleanUsername = normalize(username);
        return userRepository.findByUsername(cleanUsername)
                .filter(user -> user.getPassword().equals(password));
    }

    public AppUser register(String username, String password, String fullName) {
        String cleanUsername = normalize(username);
        if (cleanUsername.isBlank()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        if (userRepository.existsByUsername(cleanUsername)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        String displayName = fullName == null || fullName.isBlank() ? cleanUsername : fullName.trim();
        return userRepository.save(new AppUser(cleanUsername, password, displayName));
    }

    private void seedUser(String username, String password, String fullName, String role, String assignedAmbulanceId) {
        AppUser user = userRepository.findByUsername(username)
                .orElseGet(() -> new AppUser(username, password, fullName));

        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(role);
        user.setAssignedAmbulanceId(assignedAmbulanceId);
        userRepository.save(user);
    }

    private String normalize(String username) {
        return username == null ? "" : username.trim().toLowerCase();
    }
}
