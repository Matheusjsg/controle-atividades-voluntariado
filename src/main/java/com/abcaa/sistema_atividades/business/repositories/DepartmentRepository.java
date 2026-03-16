package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
