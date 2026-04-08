package com.wellnest.controller;

import com.wellnest.model.User;
import com.wellnest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        List<Map<String, String>> students = userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .map(u -> Map.of(
                    "id", u.getId(),
                    "name", u.getName(),
                    "email", u.getEmail(),
                    "createdAt", u.getCreatedAt() != null ? u.getCreatedAt().toString() : ""
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(Map.of("students", students, "total", students.size()));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long totalStudents = userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .count();
        return ResponseEntity.ok(Map.of("totalStudents", totalStudents));
    }
}
