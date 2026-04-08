package com.wellnest.repository;

import com.wellnest.model.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HealthLogRepository extends JpaRepository<HealthLog, String> {
    List<HealthLog> findByUserIdOrderByLogDateDesc(String userId);
    Optional<HealthLog> findTopByUserIdOrderByLogDateDesc(String userId);
}
