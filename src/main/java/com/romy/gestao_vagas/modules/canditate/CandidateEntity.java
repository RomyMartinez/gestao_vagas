package com.romy.gestao_vagas.modules.canditate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message ="O campo (username) não deve conter espaços")
    private String username;
    
    @NotBlank(message = "O campo (email) não deve estar vazios")
    @Email(message = "O campo (email) deve conter um e-mail valido")
    private String email;

    @Length(min = 10, max =100, message ="Deve conter mais 10 caracteres")
    private String password;
    private String description;
    private String curriculum;


}
