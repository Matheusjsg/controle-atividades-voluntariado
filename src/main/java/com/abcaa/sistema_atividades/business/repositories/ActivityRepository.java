package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Activity;
import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ActivityRepository extends JpaRepository <Activity, Long> {
    
List<Activity> findByVolunteerId(Long volunteerId);
List<Activity> findByActivityStatus(ActivityStatus status);

@Query("SELECT a FROM Activity a WHERE a.volunteer.id = :volunteerId AND a.activityStatus = 'APPROVED' AND (:startDate IS NULL OR a.date >= :startDate) AND (:endDate IS NULL OR a.date <= :endDate)")
List<Activity> findApprovedByVolunteerAndPeriod(@Param("volunteerId") Long volunteerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
