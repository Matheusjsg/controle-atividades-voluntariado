package com.abcaa.sistema_atividades.business.mapper;

import com.abcaa.sistema_atividades.business.dto.SetorDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;

public class SetorMapper {

    public static Setor toEntity(SetorDTO dto){

        Setor setor = new Setor();

        setor.setId(dto.getId());
        setor.setNome(dto.getNome());

        return setor;
    }

    public static SetorDTO toDTO(Setor setor){

        SetorDTO dto = new SetorDTO();

        dto.setId(setor.getId());
        dto.setNome(setor.getNome());

        return dto;
    }


}
