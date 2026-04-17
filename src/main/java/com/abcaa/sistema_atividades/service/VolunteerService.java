package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.PagedResponseDTO;
import com.abcaa.sistema_atividades.dto.VolunteerDTO;
import com.abcaa.sistema_atividades.domain.entity.Department;
import com.abcaa.sistema_atividades.domain.entity.Volunteer;
import com.abcaa.sistema_atividades.domain.enums.UserType;
import com.abcaa.sistema_atividades.mapper.VolunteerMapper;
import com.abcaa.sistema_atividades.repository.ActivityRepository;
import com.abcaa.sistema_atividades.repository.DepartmentRepository;
import com.abcaa.sistema_atividades.repository.VolunteerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;
    private final DepartmentRepository departmentRepository;
    private final ActivityRepository activityRepository;

    public VolunteerService(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper, DepartmentRepository departmentRepository, ActivityRepository activityRepository) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
        this.departmentRepository = departmentRepository;
        this.activityRepository = activityRepository;
    }


    public VolunteerDTO create(VolunteerDTO dto){

        Volunteer volunteer = volunteerMapper.toEntity(dto);

        //CRIANDO VOLUNTARIO COMO PADRÃO !!!!TEMPORARIAMENTE!!!
        volunteer.setUserType(UserType.VOLUNTEER);

        volunteer = volunteerRepository.save(volunteer);

        return volunteerMapper.toDTO(volunteer);
    }


    public PagedResponseDTO<VolunteerDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Volunteer> result = volunteerRepository.findAll(pageable);

        List<VolunteerDTO> content = result.getContent()
                .stream()
                .map(volunteerMapper::toDTO)
                .collect(Collectors.toList());

        // Extrai apenas os IDs da página atual para evitar N queries.
        // Uma única query agrega os minutos de todos os voluntários da página.
        LocalDate now = LocalDate.now();
        List<Long> ids = content.stream().map(VolunteerDTO::getId).collect(Collectors.toList());

        List<Object[]> rows = activityRepository.findMonthlyMinutesByVolunteerIds(ids, now.getMonthValue(), now.getYear());

        // Converte para Map<volunteerId, totalMinutes> para lookup O(1)
        Map<Long, Integer> minutesByVolunteer = rows.stream()
                .collect(Collectors.toMap(
                        row -> (Long)    row[0],
                        row -> ((Number) row[1]).intValue()
                ));

        // Injeta as horas em cada DTO (0 se não houver atividades no mês)
        content.forEach(dto -> {
            int minutes = minutesByVolunteer.getOrDefault(dto.getId(), 0);
            dto.setMonthlyMinutes(minutes);
            dto.setMonthlyHours(minutes / 60.0);
        });

        return new PagedResponseDTO<>(content, result.getNumber(),
                result.getSize(), result.getTotalElements(), result.getTotalPages());
    }




    public VolunteerDTO findById(Long id){

        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        VolunteerDTO dto = volunteerMapper.toDTO(volunteer);

        // Busca o total de minutos aprovados no mês corrente para este voluntário.
        // Retorna null se não houver atividades; tratamos como 0.
        LocalDate now = LocalDate.now();
        Integer minutes = activityRepository.findMonthlyMinutesByVolunteerId(id, now.getMonthValue(), now.getYear());
        int totalMinutes = (minutes != null) ? minutes : 0;

        dto.setMonthlyMinutes(totalMinutes);
        dto.setMonthlyHours(totalMinutes / 60.0);

                return dto;
    }



    public VolunteerDTO volunteerUpdate(Long id, VolunteerDTO dto){

        Volunteer existingVolunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));

        existingVolunteer.setName(dto.getName());
        existingVolunteer.setEmail(dto.getEmail());

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado."));

        existingVolunteer.setDepartment(department);
        existingVolunteer.setUserType(dto.getUserType());

        Volunteer updatedVolunteer = volunteerRepository.save(existingVolunteer);

         return volunteerMapper.toDTO(updatedVolunteer);

    }


    public void deleteVolunteer(Long id){
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));
        volunteerRepository.deleteById(volunteer.getId());
    }

    public VolunteerDTO updateUserType(Long id, UserType userType) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado."));
        volunteer.setUserType(userType);
        return volunteerMapper.toDTO(volunteerRepository.save(volunteer));
    }

}