package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.AtividadeDTO;
import com.abcaa.sistema_atividades.entities.Atividade;
import com.abcaa.sistema_atividades.entities.Voluntario;
import com.abcaa.sistema_atividades.mapper.AtividadeMapper;
import com.abcaa.sistema_atividades.repository.AtividadeRepository;
import com.abcaa.sistema_atividades.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final VoluntarioRepository voluntarioRepository;

    public AtividadeService(AtividadeRepository atividadeRepository,
                            VoluntarioRepository voluntarioRepository) {

        this.atividadeRepository = atividadeRepository;
        this.voluntarioRepository = voluntarioRepository;
    }

    public AtividadeDTO criar(AtividadeDTO dto){

        Voluntario voluntario = voluntarioRepository
                .findById(dto.getVoluntarioId())
                .orElseThrow();

        Atividade atividade = AtividadeMapper.toEntity(dto, voluntario);

        atividade = atividadeRepository.save(atividade);

        return AtividadeMapper.toDTO(atividade);
    }

    public List<AtividadeDTO> listar(){

        return atividadeRepository.findAll()
                .stream()
                .map(AtividadeMapper::toDTO)
                .collect(Collectors.toList());
    }

}