package com.wellnest.service;

import com.wellnest.dto.HealthLogDTO;
import com.wellnest.model.HealthLog;
import com.wellnest.model.User;
import com.wellnest.repository.HealthLogRepository;
import com.wellnest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthLogService {

    private final HealthLogRepository healthLogRepository;
    private final UserRepository userRepository;

    public HealthLogService(HealthLogRepository healthLogRepository, UserRepository userRepository) {
        this.healthLogRepository = healthLogRepository;
        this.userRepository = userRepository;
    }

    public HealthLogDTO saveLog(HealthLogDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        HealthLog log = new HealthLog();
        log.setUser(user);
        log.setMood(dto.getMood());
        log.setWaterIntake(dto.getWaterIntake());
        log.setSteps(dto.getSteps());
        log = healthLogRepository.save(log);
        return toDTO(log);
    }

    public HealthLogDTO getLatestLog(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return healthLogRepository.findTopByUserIdOrderByLogDateDesc(user.getId())
                .map(this::toDTO)
                .orElse(null);
    }

    public List<HealthLogDTO> getAllLogs(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return healthLogRepository.findByUserIdOrderByLogDateDesc(user.getId())
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private HealthLogDTO toDTO(HealthLog log) {
        HealthLogDTO dto = new HealthLogDTO();
        dto.setId(log.getId());
        dto.setMood(log.getMood());
        dto.setWaterIntake(log.getWaterIntake());
        dto.setSteps(log.getSteps());
        if (log.getLogDate() != null) dto.setLogDate(log.getLogDate().toString());
        return dto;
    }
}
