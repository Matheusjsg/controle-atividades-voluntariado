package com.abcaa.sistema_atividades.dto;

import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import com.abcaa.sistema_atividades.validation.ValidDuration;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class ActivityDTO {
    @Schema(description = "ID da atividade", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Data de realização da atividade", example = "2024-01-15", required = true)
    private LocalDate date;

    @Schema(description = "Titulo da atividade", example = "Distribuição de atividades")
    private String title;

    @Schema(description = "Descrição detalhada da atividade realizada", example = "Organização de doações e atendimento ao público")
    private String description;

    @ValidDuration
    @Schema(description = "Duração da atividade em minutos", example = "120", required = true)
    private Integer durationMinutes;

    @Schema(description = "ID do voluntário responsável", example = "5", required = true)
    private Long volunteerId;

    @Schema(description = "Nome do voluntário responsável", example = "João Silva", accessMode = Schema.AccessMode.READ_ONLY)
    private String volunteerName;

    @Schema(description = "ID do departamento do responsável da atividade", example = "5", required = true)
    private Long departmentId;

    @Schema(description = "Nome do departamento do responsável da atividade", example = "Administração", required = true)
    private String departmentName;

    @Schema(description = "Status atual da atividade", example = "PENDING")
    private ActivityStatus activityStatus;

    public ActivityDTO() {
    }

    public ActivityDTO(Long id, LocalDate date, String title, String description, Integer durationMinutes, Long volunteerId, String volunteerName, Long departmentId, String departmentName, ActivityStatus activityStatus) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.activityStatus = activityStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }
}
