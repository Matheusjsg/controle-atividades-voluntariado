package com.abcaa.sistema_atividades.business.entities;

import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name= "tb_activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate date;

    private String description;

    @Column (name = "duration_minutes")
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    @Column (name = "activity_status")
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;


    public Activity() {}

    public Activity(Long id, String title, LocalDate date, String description, Integer durationMinutes, Volunteer volunteer, ActivityStatus activityStatus) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.volunteer = volunteer;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }
}

