package com.rotina.minhaRotina.dto.response;

import com.rotina.minhaRotina.enums.Sexo;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private Sexo sexo;
    private String telefone;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}