package com.abcaa.sistema_atividades.business.mapper;

import com.abcaa.sistema_atividades.business.dto.ActivityDTO;
import com.abcaa.sistema_atividades.business.entities.Activity;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import org.springframework.stereotype.Component;

@Component

public class ActivityMapper {

    public Activity toEntity(ActivityDTO dto, Volunteer volunteer){

        Activity activity = new Activity();

        activity.setDate(dto.getDate());
        activity.setDescription(dto.getDescription());
        activity.setDurationMinutes(dto.getDurationMinutes());
        activity.setVolunteer(volunteer);
        activity.setActivityStatus(dto.getActivityStatus());


        return activity;
    }

    public ActivityDTO toDTO(Activity activity){

        ActivityDTO dto = new ActivityDTO();

        dto.setId(activity.getId());
        dto.setDate(activity.getDate());
        dto.setDescription(activity.getDescription());
        dto.setDurationMinutes(activity.getDurationMinutes());
        dto.setVolunteerId(activity.getVolunteer().getId());
        dto.setVolunteerName(activity.getVolunteer().getName());
        dto.setActivityStatus(activity.getActivityStatus());

        return dto;
    }
}
