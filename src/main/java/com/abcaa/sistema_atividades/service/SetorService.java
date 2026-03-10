package com.abcaa.sistema_atividades.service;

import com.abcaa.sistema_atividades.dto.SetorDTO;
import com.abcaa.sistema_atividades.entities.Setor;
import com.abcaa.sistema_atividades.mapper.SetorMapper;
import com.abcaa.sistema_atividades.repository.SetorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public SetorDTO criar(SetorDTO dto){

        Setor setorEntity = SetorMapper.toEntity(dto);

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
