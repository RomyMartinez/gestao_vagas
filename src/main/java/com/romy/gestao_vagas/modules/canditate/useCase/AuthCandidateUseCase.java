package com.romy.gestao_vagas.modules.canditate.useCase;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.romy.gestao_vagas.modules.canditate.CandidateRepository;
import com.romy.gestao_vagas.modules.canditate.dto.AuthCandidateRequestDTO;
import com.romy.gestao_vagas.modules.canditate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {
    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) {
        var candidate = this.candidateRepository
            .findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Candidate not found");
            });

        var passwordMatches = this.passwordEncoder.matches(
            authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new UsernameNotFoundException("Invalid password");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(candidate.getId().toString())
            .withClaim("roles", Arrays.asList("CANDIDATE")) // Assuming a single role for candidates
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // Token valid for 1 hour
            .sign(algorithm);

        return AuthCandidateResponseDTO.builder()
            .access_token(token)
            .build();
    }
}
