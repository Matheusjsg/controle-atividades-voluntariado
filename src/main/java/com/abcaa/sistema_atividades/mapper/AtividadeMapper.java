package com.abcaa.sistema_atividades.mapper;

import com.abcaa.sistema_atividades.dto.AtividadeDTO;
import com.abcaa.sistema_atividades.entities.Atividade;
import com.abcaa.sistema_atividades.entities.Voluntario;

public class AtividadeMapper {

    public static Atividade toEntity(AtividadeDTO dto, Voluntario voluntario){

        Atividade atividade = new Atividade();

        atividade.setData(dto.getData());
        atividade.setDescricao(dto.getDescricao());
        atividade.setTempoMinutos(dto.getTempoMinutos());
        atividade.setVoluntario(voluntario);

        return atividade;
    }

    public static AtividadeDTO toDTO(Atividade atividade){

        AtividadeDTO dto = new AtividadeDTO();

        dto.setId(atividade.getId());
        dto.setData(atividade.getData());
        dto.setDescricao(atividade.getDescricao());
        dto.setTempoMinutos(atividade.getTempoMinutos());
        dto.setVoluntarioId(atividade.getVoluntario().getId());

        return dto;
    }
}
