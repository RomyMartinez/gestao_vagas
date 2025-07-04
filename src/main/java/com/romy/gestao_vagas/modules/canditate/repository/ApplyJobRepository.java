package com.romy.gestao_vagas.modules.canditate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romy.gestao_vagas.modules.canditate.entity.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
    
}
