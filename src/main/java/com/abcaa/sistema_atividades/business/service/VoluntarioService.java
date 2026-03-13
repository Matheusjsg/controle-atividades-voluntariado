package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.VoluntarioDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;
import com.abcaa.sistema_atividades.business.entities.Voluntario;
import com.abcaa.sistema_atividades.business.enums.TipoUsuario;
import com.abcaa.sistema_atividades.business.mapper.VoluntarioMapper;
import com.abcaa.sistema_atividades.business.repositories.SetorRepository;
import com.abcaa.sistema_atividades.business.repositories.VoluntarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final VoluntarioMapper voluntarioMapper;
    private final SetorRepository setorRepository;

    public VoluntarioService(VoluntarioRepository voluntarioRepository, VoluntarioMapper voluntarioMapper, SetorRepository setorRepository) {
        this.voluntarioRepository = voluntarioRepository;
        this.voluntarioMapper = voluntarioMapper;
        this.setorRepository = setorRepository;
    }


    public VoluntarioDTO criar(VoluntarioDTO dto){

        Voluntario voluntario = voluntarioMapper.toEntity(dto);

        voluntario.setTipoUsuario(TipoUsuario.VOLUNTARIO);

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));


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