package com.abcaa.sistema_atividades.business.mapper;

import com.abcaa.sistema_atividades.business.dto.AtividadeDTO;
import com.abcaa.sistema_atividades.business.entities.Atividade;
import com.abcaa.sistema_atividades.business.entities.Voluntario;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class AtividadeMapper {

    public static Atividade toEntity(AtividadeDTO dto, Voluntario voluntario){

        Atividade atividade = new Atividade();

        atividade.setData(dto.getData());
        atividade.setDescricao(dto.getDescricao());
        atividade.setTempoMinutos(dto.getTempoMinutos());
        atividade.setVoluntario(voluntario);

        return atividade;
    }

    public AtividadeDTO toDTO(Atividade atividade){

        AtividadeDTO dto = new AtividadeDTO();

        dto.setId(atividade.getId());
        dto.setData(atividade.getData());
        dto.setDescricao(atividade.getDescricao());
        dto.setTempoMinutos(atividade.getTempoMinutos());
        dto.setVoluntarioId(atividade.getVoluntario().getId());
        dto.setStatus(atividade.getStatus());

        return dto;
    }
}
