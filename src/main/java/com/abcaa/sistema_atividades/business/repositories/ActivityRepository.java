package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository <Activity, Long> {
}
