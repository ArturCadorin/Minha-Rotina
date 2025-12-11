package com.rotina.minhaRotina.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TarefaRequestDTO {

    @NotBlank(message = "A Descrição é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "A Data inicial é obrigatória")
    @FutureOrPresent(message = "Data inicial deve ser hoje ou no futuro")
    private LocalDateTime dataInicial;

    @Future(message = "A Data final deve ser no futuro")
    private LocalDateTime dataFinal;

    @NotNull(message = "O Campo 'ehAgendado' é obrigatório")
    private Boolean ehAgendado;

    private String observacoes;

    @NotNull(message = "O ID do usuário é obrigatório")
    @Positive(message = "O ID do usuário deve ser positivo")
    private Long usuarioId;
}