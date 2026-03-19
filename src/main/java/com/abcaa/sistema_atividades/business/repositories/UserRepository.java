package com.abcaa.sistema_atividades.business.repositories;

import com.abcaa.sistema_atividades.business.entities.TbUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TbUser, Long> {

    boolean existsByEmail(String email);

    Optional<TbUser> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}