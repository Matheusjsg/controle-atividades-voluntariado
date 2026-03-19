package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.ActivityDTO;
import com.abcaa.sistema_atividades.business.entities.Activity;
import com.abcaa.sistema_atividades.business.entities.Volunteer;
import com.abcaa.sistema_atividades.business.enums.ActivityStatus;
import com.abcaa.sistema_atividades.business.mapper.ActivityMapper;
import com.abcaa.sistema_atividades.business.repositories.ActivityRepository;
import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final VolunteerRepository volunteerRepository;
    private final ActivityMapper activityMapper;

    public ActivityService(ActivityRepository activityRepository, VolunteerRepository volunteerRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityMapper = activityMapper;
    }
    @Transactional
    public ActivityDTO create(ActivityDTO dto){

        Volunteer volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow();

        Activity activity = activityMapper.toEntity(dto, volunteer);

        //mantendo o status inicial como  "PENDENTE"
        activity.setActivityStatus(ActivityStatus.PENDING);

        Activity savedactivity = activityRepository.save(activity);

        return activityMapper.toDTO(activity);
    }

    @Transactional
    public ActivityDTO findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada."));
        return activityMapper.toDTO(activity);
    }


    @Transactional
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    //buscar atividades pelo ID do voluntário
    @Transactional
    public List<ActivityDTO> findByVolunteerId(Long volunteerId) {
        return activityRepository.findByVolunteerId(volunteerId).stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }


    //buscar atividades por status
    @Transactional
    public List<ActivityDTO> findActivitiesByStatus(ActivityStatus status) {
        return activityRepository.findByActivityStatus(status).stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }



    @Transactional
    public ActivityDTO activityUpdate(Long id, ActivityDTO activityDTO) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity não encontrada."));

        existingActivity.setDate(activityDTO.getDate());
        existingActivity.setDescription(activityDTO.getDescription());
        existingActivity.setDurationMinutes(activityDTO.getDurationMinutes());

        Volunteer volunteer = volunteerRepository.findById(activityDTO.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer não encontrado."));

        existingActivity.setVolunteer(volunteer);

        Activity updatedActivity = activityRepository.save(existingActivity);
        return activityMapper.toDTO(updatedActivity);
    }


    @Transactional
    public ActivityDTO statusUpdate(Long id, ActivityStatus newStatus) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity não encontrado."));

        activity.setActivityStatus(newStatus);
        Activity updated = activityRepository.save(activity);

        return activityMapper.toDTO(updated);
    }


    public List<ActivityDTO> findAll(){

        return activityRepository.findAll()
                .stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }
}