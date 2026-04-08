package com.abcaa.sistema_atividades.repository;

import com.abcaa.sistema_atividades.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
