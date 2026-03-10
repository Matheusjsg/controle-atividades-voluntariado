package com.abcaa.sistema_atividades.repository;

import com.abcaa.sistema_atividades.entities.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository <Long, Atividade> {
}
