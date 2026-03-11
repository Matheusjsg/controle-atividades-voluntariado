package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.AtividadeDTO;
import com.abcaa.sistema_atividades.business.entities.Atividade;
import com.abcaa.sistema_atividades.business.entities.Voluntario;
import com.abcaa.sistema_atividades.business.enums.StatusAtividade;
import com.abcaa.sistema_atividades.business.mapper.AtividadeMapper;
import com.abcaa.sistema_atividades.business.repositories.AtividadeRepository;
import com.abcaa.sistema_atividades.business.repositories.VoluntarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final AtividadeMapper atividadeMapper;

        public AtividadeService(AtividadeRepository atividadeRepository, VoluntarioRepository voluntarioRepository, AtividadeMapper atividadeMapper) {
        this.atividadeRepository = atividadeRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.atividadeMapper = atividadeMapper;
    }

    public AtividadeDTO criar(AtividadeDTO dto){

        Voluntario voluntario = voluntarioRepository.findById(dto.getVoluntarioId())
                .orElseThrow();

        Atividade atividade = atividadeMapper.toEntity(dto, voluntario);
        //mantendo o staus inicial de pendente
        atividade.setStatus(StatusAtividade.PENDENTE);
        atividade = atividadeRepository.save(atividade);

        return atividadeMapper.toDTO(atividade);
    }

    public List<AtividadeDTO> listar(){

        return atividadeRepository.findAll()
                .stream()
                .map(atividadeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeDTO atualizarStatus(Long id, StatusAtividade novoStatus) {
        Atividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrado."));

        atividade.setStatus(novoStatus);
        Atividade atualizado = atividadeRepository.save(atividade);

        return atividadeMapper.toDTO(atualizado);
    }

}