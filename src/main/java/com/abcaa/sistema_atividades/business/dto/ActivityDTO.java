package com.abcaa.sistema_atividades.business.dto;

import com.abcaa.sistema_atividades.business.enums.ActivityStatus;

import java.time.LocalDate;

public class ActivityDTO {
    private Long id;

    private LocalDate date;

    private String description;

    private Integer durationMinutes;

    private Long volunteerId;

    private ActivityStatus activityStatus;

    public ActivityDTO() {}

    public ActivityDTO(Long id, LocalDate date, String description, Integer durationMinutes, Long volunteerId, ActivityStatus activityStatus) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.volunteerId = volunteerId;
        this.activityStatus = activityStatus;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }
}
