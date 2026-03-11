package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository <Atividade, Long> {
}
