package com.wellnest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
            "status", "online",
            "message", "Welcome to WellNest Health Platform API",
            "documentation", "/swagger-ui/index.html"
        );
    }
}
