package com.rotina.minhaRotina.dto.request;

import com.rotina.minhaRotina.enums.IntensidadeTreino;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.enums.ObjetivoTreino;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TreinoRequestDTO extends TarefaRequestDTO {

    private ObjetivoTreino objetivo;
    private IntensidadeTreino intensidade;
    private ModalidadeTreino modalidade;

    @PositiveOrZero(message = "As Horas totais não podem ser menor que zero!")
    private Long horasTotais;

    // Campos para treino de força
    @PositiveOrZero(message = "As Séries não podem ser menor que zero!")
    private Integer series;

    @PositiveOrZero(message = "As Repetições não podem ser menor que zero!")
    private Integer repeticoes;

    @PositiveOrZero(message = "A Carga por repetição não podem ser menor que zero!")
    private Double cargaRepeticao;

    private List<String> gruposMusculares;

    // Campos para treino cardio
    @PositiveOrZero(message = "A Distância não podem ser menor que zero!")
    private Double distancia;

    @PositiveOrZero(message = "A Elevação acumulada não podem ser menor que zero!")
    private Double elevacaoAcumulada;

    @PositiveOrZero(message = "A Elevação máxima não podem ser menor que zero!")
    private Double elevacaoMaxima;

    @PositiveOrZero(message = "A Velocidade média não podem ser menor que zero!")
    private Double velocidadeMedia;

    @PositiveOrZero(message = "A Velocidade máxima não podem ser menor que zero!")
    private Double velocidadeMaxima;

    private List<String> equipamentos;

    @PositiveOrZero(message = "A Frequência cardíaca máxima não podem ser menor que zero!")
    private Double frequenciaCardiacaMaxima;

    @PositiveOrZero(message = "A Frequência cardíaca média não podem ser menor que zero!")
    private Double frequenciaCardiacaMedia;
}