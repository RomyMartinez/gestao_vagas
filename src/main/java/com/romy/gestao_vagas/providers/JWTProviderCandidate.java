package com.romy.gestao_vagas.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProviderCandidate {
    @Value("${security.token.secret.candidate}")
    private String secretKey;
    
    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        try {
            var tokenDecoded = JWT.require(algorithm)
                .build()
                .verify(token);
                
            return tokenDecoded;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null or handle the exception as needed
        }

    }
}
