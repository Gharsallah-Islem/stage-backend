package com.exam_management.stage.controller;

import com.exam_management.stage.entity.User;
import com.exam_management.stage.service.UserService;
import com.exam_management.stage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.registerUser(user);
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            Map<String, String> authResponse = userService.authenticateUser(email, password);

            User user = userRepository.findByEmail(email);
            Map<String, Object> response = new HashMap<>(authResponse);
            response.put("userId", user.getId());
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}