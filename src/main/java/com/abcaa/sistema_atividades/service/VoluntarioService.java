package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.VoluntarioDTO;
import com.abcaa.sistema_atividades.entities.Setor;
import com.abcaa.sistema_atividades.entities.Voluntario;
import com.abcaa.sistema_atividades.mapper.VoluntarioMapper;
import com.abcaa.sistema_atividades.repository.SetorRepository;
import com.abcaa.sistema_atividades.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioMapper voluntarioMapper;

    public VoluntarioService(VoluntarioRepository voluntarioRepository, VoluntarioMapper voluntarioMapper) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioMapper = voluntarioMapper;
    }



    public VoluntarioDTO criar(VoluntarioDTO dto, Setor setorDto){

        Voluntario voluntario = VoluntarioMapper.toEntity(dto, setorDto);

        voluntario = voluntarioRepository.save(voluntario);

        return VoluntarioMapper.toDTO(voluntario);
    }

    public List<VoluntarioDTO> listar(){

        return voluntarioRepository.findAll()
                .stream()
                .map(VoluntarioMapper::toDTO)
                .collect(Collectors.toList());
    }

}