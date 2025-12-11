package com.rotina.minhaRotina.dto.common;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MensagemResponseDTO {
    private String mensagem;
    private LocalDateTime timestamp;
    private Integer status;

    public MensagemResponseDTO(String mensagem, Integer status) {
        this.mensagem = mensagem;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public MensagemResponseDTO(String mensagem) {
        this(mensagem, 200);
    }
}
