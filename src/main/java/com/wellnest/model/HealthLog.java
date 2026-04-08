package com.wellnest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_logs")
public class HealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int mood;
    private int waterIntake;
    private int steps;

    @Column(name = "log_date")
    private LocalDateTime logDate = LocalDateTime.now();

    public HealthLog() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public int getMood() { return mood; }
    public void setMood(int mood) { this.mood = mood; }
    public int getWaterIntake() { return waterIntake; }
    public void setWaterIntake(int waterIntake) { this.waterIntake = waterIntake; }
    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }
    public LocalDateTime getLogDate() { return logDate; }
    public void setLogDate(LocalDateTime logDate) { this.logDate = logDate; }
}
