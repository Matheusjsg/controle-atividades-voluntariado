package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.ActivityDTO;
import com.abcaa.sistema_atividades.dto.ActivityReportDTO;
import com.abcaa.sistema_atividades.dto.PagedResponseDTO;
import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import com.abcaa.sistema_atividades.dto.VolunteerRankingDTO;
import com.abcaa.sistema_atividades.mapper.ActivityMapper;
import com.abcaa.sistema_atividades.repository.ActivityRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import com.abcaa.sistema_atividades.validation.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final VolunteerRepository volunteerRepository;
    private final ActivityMapper activityMapper;
    private final ValidationService validationService;

    public ActivityService(ActivityRepository activityRepository, VolunteerRepository volunteerRepository, ActivityMapper activityMapper, ValidationService validationService) {
        this.activityRepository = activityRepository;
        this.volunteerRepository = volunteerRepository;
        this.activityMapper = activityMapper;
        this.validationService = validationService;
    }
    @Transactional
    public ActivityDTO create(ActivityDTO dto){
        validationService.validateActivity(dto);

        Volunteer volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        Activity activity = activityMapper.toEntity(dto, volunteer);

        //mantendo o status inicial como  "PENDENTE"
        activity.setActivityStatus(ActivityStatus.PENDING);

        Activity savedactivity = activityRepository.save(activity);

        return activityMapper.toDTO(savedactivity);
    }

    @Transactional
    public ActivityDTO findById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada."));
        return activityMapper.toDTO(activity);
    }


    @Transactional
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }


    //buscar atividades pelo ID do voluntário
    @Transactional
    public PagedResponseDTO<ActivityDTO> findByVolunteerId(Long volunteerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> result = activityRepository.findByVolunteerId(volunteerId, pageable);

        List<ActivityDTO> content = result.getContent()
                .stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(content, result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
    }


    //buscar atividades por status
    @Transactional
    public PagedResponseDTO<ActivityDTO> findActivitiesByStatus(ActivityStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> result = activityRepository.findByActivityStatus(status, pageable);

        List<ActivityDTO> content = result.getContent()
                .stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(content, result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
    }



    @Transactional
    public ActivityDTO activityUpdate(Long id, ActivityDTO activityDTO) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada."));

        validationService.validateActivityUpdate(existingActivity);
        validationService.validateActivity(activityDTO);

        existingActivity.setDate(activityDTO.getDate());
        existingActivity.setTitle(activityDTO.getTitle());
        existingActivity.setDescription(activityDTO.getDescription());
        existingActivity.setDurationMinutes(activityDTO.getDurationMinutes());

        Volunteer volunteer = volunteerRepository.findById(activityDTO.getVolunteerId())
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        existingActivity.setVolunteer(volunteer);

        Activity updatedActivity = activityRepository.save(existingActivity);
        return activityMapper.toDTO(updatedActivity);
    }


    @Transactional
    public ActivityDTO statusUpdate(Long id, ActivityStatus newStatus) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada."));

        activity.setActivityStatus(newStatus);
        Activity updated = activityRepository.save(activity);

        return activityMapper.toDTO(updated);
    }


      public PagedResponseDTO<ActivityDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Activity> result = activityRepository.findAll(pageable);

        List<ActivityDTO> content = result.getContent()
                .stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(content, result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
    }



    public ActivityReportDTO getReport(Long volunteerId, LocalDate startDate, LocalDate endDate) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        List<Activity> activities;
        
        if (startDate != null && endDate != null) {
            activities = activityRepository.findByVolunteerIdAndActivityStatusAndDateBetween(
                volunteerId, ActivityStatus.APPROVED, startDate, endDate);
        } else if (startDate != null) {
            activities = activityRepository.findByVolunteerIdAndActivityStatusAndDateGreaterThanEqual(
                volunteerId, ActivityStatus.APPROVED, startDate);
        } else if (endDate != null) {
            activities = activityRepository.findByVolunteerIdAndActivityStatusAndDateLessThanEqual(
                volunteerId, ActivityStatus.APPROVED, endDate);
        } else {
            activities = activityRepository.findByVolunteerIdAndActivityStatus(
                volunteerId, ActivityStatus.APPROVED);
        }

        int totalMinutes = activities.stream().mapToInt(Activity::getDurationMinutes).sum();
        List<ActivityDTO> activityDTOs = activities.stream().map(activityMapper::toDTO).collect(Collectors.toList());

        return new ActivityReportDTO(
                volunteer.getId(),
                volunteer.getName(),
                volunteer.getDepartment().getName(),
                totalMinutes,
                activities.size(),
                activityDTOs
        );
    }


// Ranking de voluntários por horas aprovadas em um período.
// startDate e endDate são opcionais:
//   - Sem filtro  → acumulado geral de todos os tempos
//   - Com filtro  → apenas atividades dentro do intervalo informado
//
// A posição (rank) é atribuída após a query, que já retorna ordenado
// do maior para o menor. Em caso de empate, a ordem é alfabética
// (definida pelo banco via ORDER BY na query).

    public List<VolunteerRankingDTO> getRanking(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
            endDate   = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        }
        List<Object[]> rows = activityRepository.findRanking(startDate, endDate);

        List<VolunteerRankingDTO> ranking = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);

            Long volunteerId= (Long) row[0];
            String volunteerName= (String) row[1];
            String departmentName= (String) row[2];
            int totalMinutes= ((Number) row[3]).intValue();
            int totalActivities= ((Number) row[4]).intValue();

            // Posição começa em 1
            ranking.add(new VolunteerRankingDTO(
                    i + 1,
                    volunteerId,
                    volunteerName,
                    departmentName,
                    totalMinutes,
                    totalActivities
            ));
        }

        return ranking;
    }





}