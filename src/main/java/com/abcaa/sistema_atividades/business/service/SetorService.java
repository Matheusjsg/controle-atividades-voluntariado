package com.abcaa.sistema_atividades.business.service;

import com.abcaa.sistema_atividades.business.dto.SetorDTO;
import com.abcaa.sistema_atividades.business.entities.Setor;
import com.abcaa.sistema_atividades.business.mapper.SetorMapper;
import com.abcaa.sistema_atividades.business.repositories.SetorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;

    public SetorService(SetorRepository setorRepository, SetorMapper setorMapper) {
        this.setorRepository = setorRepository;
        this.setorMapper = setorMapper;
    }

    public SetorDTO criar(SetorDTO dto){

        Setor setorEntity = setorMapper.toEntity(dto);

        Setor salvo = setorRepository.save(setorEntity);

        return SetorMapper.toDTO(salvo);
    }

    public List<SetorDTO> listar(){

        return setorRepository.findAll()
                .stream()
                .map(SetorMapper::toDTO)
                .collect(Collectors.toList());
    }

}
