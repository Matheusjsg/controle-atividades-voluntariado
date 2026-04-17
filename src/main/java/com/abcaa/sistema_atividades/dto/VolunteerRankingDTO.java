package com.abcaa.sistema_atividades.dto;

public class VolunteerRankingDTO {

    private Integer rank;
    private Long volunteerId;
    private String volunteerName;
    private String department;
    private Integer totalMinutes;
    private Double totalHours;
    private Integer totalActivities;

    public VolunteerRankingDTO() {
    }

    public VolunteerRankingDTO(Integer rank, Long volunteerId, String volunteerName,
                               String department, Integer totalMinutes, Integer totalActivities) {
        this.rank = rank;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.department = department;
        this.totalMinutes = totalMinutes;
        this.totalHours = totalMinutes / 60.0;
        this.totalActivities = totalActivities;
    }

    public Integer getRank() { return rank; }
    public Long getVolunteerId() { return volunteerId; }
    public String getVolunteerName() { return volunteerName; }
    public String getDepartment() { return department; }
    public Integer getTotalMinutes() { return totalMinutes; }
    public Double getTotalHours() { return totalHours; }
    public Integer getTotalActivities() { return totalActivities; }
}
