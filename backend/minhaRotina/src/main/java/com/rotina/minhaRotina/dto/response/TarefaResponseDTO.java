package com.rotina.minhaRotina.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TarefaResponseDTO {
    private Long id;
    private String descricao;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private Boolean ehAgendado;
    private String observacoes;
    private String tipoTarefa; // "ESTUDO" ou "TREINO"
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // Garantindo a exposição apenas dos dados básicos do usuário
    private UsuarioResponseDTO usuario;
}