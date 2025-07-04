package com.romy.gestao_vagas.modules.canditate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity(name = "candidate")
@Data
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "John Doe")
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message ="O campo (username) não deve conter espaços")
    @Schema(example = "john_doe")
    private String username;
    
    @NotBlank(message = "O campo (email) não deve estar vazios")
    @Email(message = "O campo (email) deve conter um e-mail valido")
    @Schema(example = "john@gmail.com")
    private String email;

    @Length(min = 10, max =100, message ="Deve conter mais 10 caracteres")
    @Schema(example = "admin123", minLength = 10, maxLength = 100)
    private String password;
    @Schema(example = "A passionate software developer with 5 years of experience in building scalable applications.")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
