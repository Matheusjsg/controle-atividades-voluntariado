package com.abcaa.sistema_atividades.validation;

import com.abcaa.sistema_atividades.dto.ActivityDTO;
import com.abcaa.sistema_atividades.domain.entity.Activity;
import com.abcaa.sistema_atividades.domain.enums.ActivityStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidationService {

    public void validateActivity(ActivityDTO dto) {
        if (dto.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Não é permitido registrar atividades com data futura.");
        }

        if (dto.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("O título da atividade é obrigatório.");
        }
    }

    public void validateActivityUpdate(Activity activity) {
        if (activity.getActivityStatus() != ActivityStatus.PENDING) {
            throw new IllegalStateException("Não é permitido editar atividades já aprovadas.");
        }
    }
}
