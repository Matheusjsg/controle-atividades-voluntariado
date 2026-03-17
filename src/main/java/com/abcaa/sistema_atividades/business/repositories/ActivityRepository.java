package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Activity;
import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActivityRepository extends JpaRepository <Activity, Long> {
    
List<Activity> findByVolunteerId(Long volunteerId);
List<Activity> findByActivityStatus(ActivityStatus status);

}
