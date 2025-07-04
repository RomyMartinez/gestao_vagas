package com.romy.gestao_vagas;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.romy.gestao_vagas.exceptions.JobNotFoundException;
import com.romy.gestao_vagas.exceptions.UserNotFoundException;
import com.romy.gestao_vagas.modules.canditate.CandidateRepository;
import com.romy.gestao_vagas.modules.canditate.entity.CandidateEntity;
import com.romy.gestao_vagas.modules.canditate.useCase.ApplyJobUseCase;
import com.romy.gestao_vagas.modules.company.repositories.JobRepository;


@ExtendWith(MockitoExtension.class)
public class ApplyJobUseCaseTest {
    
    @InjectMocks
    private ApplyJobUseCase applyJobUseCase;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should not ve able to apply job with candidate not found")
    public void shoud_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            applyJobUseCase.execute(null, null);
        } catch (Exception e) {
            assert e instanceof UserNotFoundException;
        }
    }

    @Test
    @DisplayName("Should not ve able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found(){
        var idCandidate = java.util.UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            applyJobUseCase.execute(null, idCandidate);
        } catch (Exception e) {
            assert e instanceof JobNotFoundException;
        }
    }

}
