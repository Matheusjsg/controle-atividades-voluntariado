package com.abcaa.sistema_atividades.business.repositories;


import com.abcaa.sistema_atividades.business.entities.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Telephone, Long> {
}
