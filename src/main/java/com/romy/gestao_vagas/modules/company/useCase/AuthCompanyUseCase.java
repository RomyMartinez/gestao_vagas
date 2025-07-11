package com.romy.gestao_vagas.modules.company.useCase;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.romy.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.romy.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.romy.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
            () -> {
                throw new UsernameNotFoundException("Company não foi encontrado ou senha errada");
            }
        );

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if(!passwordMatches){
            throw new AuthenticationException(); 
        }

        var duration = Instant.now().plus(Duration.ofHours(2));

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javavags")
            .withExpiresAt(duration)
            .withClaim("roles", Arrays.asList("COMPANY")) // Assuming a single role for companies
            .withSubject(company.getId().toString())
            .sign(algorithm);
        

        return AuthCompanyResponseDTO.builder()
            .access_token(token)
            .expires_in(duration.toEpochMilli()) // 2 hours in seconds
            .build();

    }   
}
