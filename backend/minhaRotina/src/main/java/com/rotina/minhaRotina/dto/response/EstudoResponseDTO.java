package com.rotina.minhaRotina.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EstudoResponseDTO extends TarefaResponseDTO {
    private String conteudo;
    private String objetivo;
    private Integer horasTotais;
    private Integer horasDiarias;
}