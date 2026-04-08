package com.wellnest.service;

import com.wellnest.dto.AuthDTOs;
import com.wellnest.model.User;
import com.wellnest.repository.UserRepository;
import com.wellnest.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Value("${wellnest.admin.access-key}")
    private String adminAccessKey;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthDTOs.AuthResponse register(AuthDTOs.RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail().toLowerCase())) {
            throw new RuntimeException("An account with this email already exists.");
        }
        User user = new User();
        user.setName(req.getName().trim());
        user.setEmail(req.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(User.Role.STUDENT);
        user = userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        return new AuthDTOs.AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthDTOs.AuthResponse login(AuthDTOs.LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }
        if (user.getRole() != User.Role.STUDENT) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        return new AuthDTOs.AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthDTOs.AuthResponse adminLogin(AuthDTOs.AdminLoginRequest req) {
        if (!req.getAccessKey().equals(adminAccessKey)) {
            throw new RuntimeException("Invalid admin access key.");
        }
        User admin = userRepository.findByEmail(req.getEmail().trim().toLowerCase())
                .orElseGet(() -> {
                    User a = new User();
                    a.setName("Administrator");
                    a.setEmail(req.getEmail().trim().toLowerCase());
                    a.setPassword(passwordEncoder.encode(req.getAccessKey()));
                    a.setRole(User.Role.ADMIN);
                    return userRepository.save(a);
                });

        if (admin.getRole() != User.Role.ADMIN) {
            admin.setRole(User.Role.ADMIN);
            admin = userRepository.save(admin);
        }

        String token = jwtUtils.generateToken(admin.getEmail(), admin.getRole().name(), admin.getId());
        return new AuthDTOs.AuthResponse(token, admin.getId(), admin.getName(), admin.getEmail(), admin.getRole().name());
    }
}
