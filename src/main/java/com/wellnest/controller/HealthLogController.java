package com.wellnest.controller;

import com.wellnest.dto.HealthLogDTO;
import com.wellnest.service.HealthLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthLogController {

    private final HealthLogService healthLogService;

    public HealthLogController(HealthLogService healthLogService) {
        this.healthLogService = healthLogService;
    }

    @PostMapping("/log")
    public HealthLogDTO saveLog(@RequestBody HealthLogDTO dto, Authentication auth) {
        return healthLogService.saveLog(dto, auth.getName());
    }

    @GetMapping("/latest")
    public ResponseEntity<HealthLogDTO> getLatest(Authentication auth) {
        HealthLogDTO log = healthLogService.getLatestLog(auth.getName());
        if (log == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(log);
    }

    @GetMapping("/history")
    public List<HealthLogDTO> getHistory(Authentication auth) {
        return healthLogService.getAllLogs(auth.getName());
    }
}
