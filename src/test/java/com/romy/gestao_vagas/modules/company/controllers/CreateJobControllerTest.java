package com.romy.gestao_vagas.modules.company.controllers;



import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.romy.gestao_vagas.modules.company.dto.CreateJobDTO;
import com.romy.gestao_vagas.modules.company.entities.CompanyEntity;
import com.romy.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.romy.gestao_vagas.utils.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers
        .springSecurity()).build();
    }
    
    @Test
    public void should_be_able_to_create_job() throws Exception {

        var company = CompanyEntity.builder()
        .username("romy")
        .email("romy@gmail.com")
        .password("123456789342")
        .name("Romy")
        .description("Romy is a software engineer")
        .website("https://romy.com")
        .build();

        company = companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder()
        .benefits("Health insurance, Flexible hours")
        .description("Software Engineer")
        .level("Senior")
        .build();

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJson(createJobDTO))
        .header("Authorization", "Bearer " + TestUtil.generateToken(company.getId(), "JAVAGAS_123"))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);

    }

    @Test
    public void should_not_be_able_to_create_job_if_company_not_found() throws Exception {
        var createJobDTO = CreateJobDTO.builder()
        .benefits("Health insurance, Flexible hours")
        .description("Software Engineer")
        .level("Senior")
        .build();
        
        
        mockMvc.perform(MockMvcRequestBuilders.post("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.objectToJson(createJobDTO))
        .header("Authorization", "Bearer " + TestUtil.generateToken(UUID.randomUUID(), "JAVAGAS_123"))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
