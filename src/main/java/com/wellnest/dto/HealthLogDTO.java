package com.wellnest.dto;

public class HealthLogDTO {
    private String id;
    private int mood;
    private int waterIntake;
    private int steps;
    private String logDate;

    public HealthLogDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getMood() { return mood; }
    public void setMood(int mood) { this.mood = mood; }
    public int getWaterIntake() { return waterIntake; }
    public void setWaterIntake(int waterIntake) { this.waterIntake = waterIntake; }
    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }
    public String getLogDate() { return logDate; }
    public void setLogDate(String logDate) { this.logDate = logDate; }
}
