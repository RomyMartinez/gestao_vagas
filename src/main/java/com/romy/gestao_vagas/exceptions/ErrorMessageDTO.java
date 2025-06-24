package com.romy.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ErrorMessageDTO {
    private String message;
    private String field;
}