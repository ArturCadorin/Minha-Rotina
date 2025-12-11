package com.rotina.minhaRotina.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EstudoRequestDTO extends TarefaRequestDTO {

    @Size(max = 255, message = "O campo 'Conteúdo' deve ter no máximo 255 caracteres")
    private String conteudo;

    @Size(max = 255, message = "O campo 'Objetivo' deve ter no máximo 255 caracteres")
    private String objetivo;

    @PositiveOrZero(message = "As Horas totais não podem ser menor que zero!")
    private Integer horasTotais;

    @PositiveOrZero(message = "As Horas diárias não podem ser menor que zero!")
    private Integer horasDiarias;
}