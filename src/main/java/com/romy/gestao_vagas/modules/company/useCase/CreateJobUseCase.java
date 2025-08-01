package com.romy.gestao_vagas.modules.company.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romy.gestao_vagas.exceptions.CompanyNotFoundExeception;
import com.romy.gestao_vagas.modules.company.entities.JobEntity;
import com.romy.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.romy.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity){
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundExeception();
        });


        return this.jobRepository.save(jobEntity); 
    }
}
