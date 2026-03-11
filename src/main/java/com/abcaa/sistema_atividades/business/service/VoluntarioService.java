package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.VoluntarioDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;
import com.abcaa.sistema_atividades.business.entities.Voluntario;
import com.abcaa.sistema_atividades.business.mapper.VoluntarioMapper;
import com.abcaa.sistema_atividades.business.repositories.VoluntarioRepository;
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

    public VoluntarioDTO dadosVoluntario(Long id){

        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Irformações não encontrada."));
        return voluntarioMapper.toDTO(voluntario);


    }

    public void deletarVoluntario(Long id){
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        voluntarioRepository.deleteById(voluntario.getId());
    }

}