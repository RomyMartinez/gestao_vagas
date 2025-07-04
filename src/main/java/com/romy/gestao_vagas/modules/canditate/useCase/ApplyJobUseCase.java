package com.romy.gestao_vagas.modules.canditate.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.romy.gestao_vagas.exceptions.JobNotFoundException;
import com.romy.gestao_vagas.exceptions.UserNotFoundException;
import com.romy.gestao_vagas.modules.canditate.CandidateRepository;
import com.romy.gestao_vagas.modules.canditate.repository.ApplyJobRepository;
import com.romy.gestao_vagas.modules.company.repositories.JobRepository;

public class ApplyJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public void execute(UUID jobId, UUID candidateId) {
        // Validate if the candidate exists
        this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UserNotFoundException();
        }
        );

        // Validate if the job exists
        this.jobRepository.findById(jobId).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        
    }
}
