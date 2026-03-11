package com.abcaa.sistema_atividades.business.mapper;


import com.abcaa.sistema_atividades.business.dto.VoluntarioDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;
import com.abcaa.sistema_atividades.business.entities.Voluntario;
import org.springframework.stereotype.Component;

@Component
public class VoluntarioMapper {


    public static Voluntario toEntity(VoluntarioDTO dto, Setor setor){

        Voluntario voluntario = new Voluntario();

        voluntario.setId(dto.getId());
        voluntario.setNome(dto.getNome());
        voluntario.setEmail(dto.getEmail());
        voluntario.setSetor(setor);

        return voluntario;
    }

    public static VoluntarioDTO toDTO(Voluntario voluntario){

        VoluntarioDTO dto = new VoluntarioDTO();

        dto.setId(voluntario.getId());
        dto.setNome(voluntario.getNome());
        dto.setEmail(voluntario.getEmail());
        dto.setSetorId(voluntario.getSetor().getId());
        dto.setTipoUsuario(voluntario.getTipoUsuario());

        return dto;
    }



}





