package com.romy.gestao_vagas.modules.canditate;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romy.gestao_vagas.modules.canditate.entity.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID>{
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String Email);
    Optional<CandidateEntity> findByUsername(String username);

}
