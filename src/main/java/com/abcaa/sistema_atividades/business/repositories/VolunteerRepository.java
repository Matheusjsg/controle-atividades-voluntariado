package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long>{
    List<Volunteer> findAllByOrderByNameAsc();
    Optional<Volunteer> findByEmail(String email);

    Page<Volunteer> findAll(Pageable pageable);
}
