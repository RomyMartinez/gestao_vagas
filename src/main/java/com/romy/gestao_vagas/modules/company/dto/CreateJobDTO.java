package com.romy.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {
    @Schema(example = "Health insurance, Flexible hours", requiredMode = RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Software Engineer", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Senior", requiredMode = RequiredMode.REQUIRED)
    private String level;

}
