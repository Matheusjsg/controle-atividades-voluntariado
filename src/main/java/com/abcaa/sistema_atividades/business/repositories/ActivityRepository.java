package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Activity;
import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;


public interface ActivityRepository extends JpaRepository <Activity, Long> {
    
List<Activity> findByVolunteerId(Long volunteerId);
List<Activity> findByActivityStatus(ActivityStatus status);
List<Activity> findByVolunteerIdAndActivityStatus(Long volunteerId, ActivityStatus status);
List<Activity> findByVolunteerIdAndActivityStatusAndDateBetween(Long volunteerId, ActivityStatus status, LocalDate startDate, LocalDate endDate);
List<Activity> findByVolunteerIdAndActivityStatusAndDateGreaterThanEqual(Long volunteerId, ActivityStatus status, LocalDate startDate);
List<Activity> findByVolunteerIdAndActivityStatusAndDateLessThanEqual(Long volunteerId, ActivityStatus status, LocalDate endDate);


    Page<Activity> findAll(Pageable pageable);
    Page<Activity> findByVolunteerId(Long volunteerId, Pageable pageable);
    Page<Activity> findByActivityStatus(ActivityStatus status, Pageable pageable);

}
