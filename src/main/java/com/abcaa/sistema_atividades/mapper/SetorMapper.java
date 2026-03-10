package com.abcaa.sistema_atividades.mapper;

import com.abcaa.sistema_atividades.dto.SetorDTO;
import com.abcaa.sistema_atividades.entities.Setor;

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
