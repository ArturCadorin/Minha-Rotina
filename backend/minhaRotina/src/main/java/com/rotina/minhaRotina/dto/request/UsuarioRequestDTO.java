package com.rotina.minhaRotina.dto.request;

import com.rotina.minhaRotina.enums.Sexo;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "O Email é obrigatório")
    @Email(message = "O Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;

    @NotBlank(message = "O Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @Past(message = "A Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    private Sexo sexo;

    @Size(max = 20, message = "O Telefone deve ter no máximo 20 caracteres")
    private String telefone;
}