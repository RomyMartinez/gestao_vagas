package com.romy.gestao_vagas.modules.candidate.useCase;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.romy.gestao_vagas.exceptions.JobNotFoundException;
import com.romy.gestao_vagas.exceptions.UserNotFoundException;
import com.romy.gestao_vagas.modules.canditate.CandidateRepository;
import com.romy.gestao_vagas.modules.canditate.entity.ApplyJobEntity;
import com.romy.gestao_vagas.modules.canditate.entity.CandidateEntity;
import com.romy.gestao_vagas.modules.canditate.repository.ApplyJobRepository;
import com.romy.gestao_vagas.modules.canditate.useCase.ApplyJobUseCase;
import com.romy.gestao_vagas.modules.company.entities.JobEntity;
import com.romy.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobUseCaseTest {

    @InjectMocks
    private ApplyJobUseCase applyJobUseCase;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should throw UserNotFoundException if candidate is null")
    void shouldThrowUserNotFoundIfCandidateNotFound() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            applyJobUseCase.execute(idCandidate, idJob);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw JobNotFoundException if job is null")
    void shouldThrowJobNotFoundIfJobNotFound() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.empty());

        Exception exception = assertThrows(JobNotFoundException.class, () -> {
            applyJobUseCase.execute(idCandidate, idJob);
        });

        assertEquals("Vaga não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Should apply to job successfully")
    void shouldApplyToJobSuccessfully() {
        UUID idCandidate = UUID.randomUUID();
        UUID idJob = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        var job = new JobEntity();
        job.setId(idJob);

        var applyJob = ApplyJobEntity.builder()
            .candidateId(idCandidate)
            .jobId(idJob)
            .build();

        var applyJobCreated = ApplyJobEntity.builder()
            .id(UUID.randomUUID())
            .candidateId(idCandidate)
            .jobId(idJob)
            .build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(job));
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobUseCase.execute(idCandidate, idJob);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(idCandidate, result.getCandidateId());
        assertEquals(idJob, result.getJobId());
    }
}
