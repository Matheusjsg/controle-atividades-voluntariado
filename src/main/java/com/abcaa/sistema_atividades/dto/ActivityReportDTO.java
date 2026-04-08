package com.abcaa.sistema_atividades.dto;

import java.util.List;

public class ActivityReportDTO {

    private Long volunteerId;
    private String volunteerName;
    private String department;
    private Integer totalMinutes;
    private Double totalHours;
    private Integer totalActivities;
    private boolean completedCycle20h;
    private List<ActivityDTO> activities;

    public ActivityReportDTO() {}

    public ActivityReportDTO(Long volunteerId, String volunteerName, String department,
                             Integer totalMinutes, Integer totalActivities, List<ActivityDTO> activities) {
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.department = department;
        this.totalMinutes = totalMinutes;
        this.totalHours = totalMinutes / 60.0;
        this.totalActivities = totalActivities;
        this.completedCycle20h = totalMinutes >= 1200;
        this.activities = activities;
    }

    public Long getVolunteerId() { return volunteerId; }
    public String getVolunteerName() { return volunteerName; }
    public String getDepartment() { return department; }
    public Integer getTotalMinutes() { return totalMinutes; }
    public Double getTotalHours() { return totalHours; }
    public Integer getTotalActivities() { return totalActivities; }
    public boolean isCompletedCycle20h() { return completedCycle20h; }
    public List<ActivityDTO> getActivities() { return activities; }
}
