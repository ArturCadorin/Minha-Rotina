package com.rotina.minhaRotina.dto.response;

import com.rotina.minhaRotina.enums.IntensidadeTreino;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.enums.ObjetivoTreino;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TreinoResponseDTO extends TarefaResponseDTO {
    private ObjetivoTreino objetivo;
    private IntensidadeTreino intensidade;
    private ModalidadeTreino modalidade;
    private Long horasTotais;
    private Integer series;
    private Integer repeticoes;
    private Double cargaRepeticao;
    private Double cargaTotal;
    private List<String> gruposMusculares;
    private Double distancia;
    private Double elevacaoAcumulada;
    private Double elevacaoMaxima;
    private Double velocidadeMedia;
    private Double velocidadeMaxima;
    private List<String> equipamentos;
    private Double frequenciaCardiacaMaxima;
    private Double frequenciaCardiacaMedia;
}