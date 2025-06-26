package com.romy.gestao_vagas.modules.canditate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.romy.gestao_vagas.modules.canditate.CandidateRepository;
import com.romy.gestao_vagas.modules.canditate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
    
    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Candidate not found with ID: " + idCandidate);
            });

        var candidateDto = ProfileCandidateResponseDTO.builder()
            .id(candidate.getId())
            .username(candidate.getUsername())
            .email(candidate.getEmail())
            .name(candidate.getName())
            .description(candidate.getDescription())
            .build();

        return candidateDto;

        
    }

}
