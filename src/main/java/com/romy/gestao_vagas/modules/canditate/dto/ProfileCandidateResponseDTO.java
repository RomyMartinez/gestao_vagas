package com.romy.gestao_vagas.modules.canditate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "john_doe")
    private String username;

    @Schema(example = "john@gmail.com")
    private String email;

    @Schema(example = "John Doe")
    private String name;
    private UUID id;

    @Schema(example = "A passionate software developer with 5 years of experience in building scalable applications.")
    private String description;

}