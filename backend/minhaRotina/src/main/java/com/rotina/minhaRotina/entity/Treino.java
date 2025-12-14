package com.rotina.minhaRotina.entity;

import com.rotina.minhaRotina.enums.IntensidadeTreino;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.enums.ObjetivoTreino;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("TREINO")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Treino extends Tarefa {

    /*
        Dados básicos do treino
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "objetivo_treino", length = 50)
    private ObjetivoTreino objetivo;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private IntensidadeTreino intensidade;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ModalidadeTreino modalidade;

    @Column(name = "horas_totais")
    private Long horasTotais;

    /*
        Campos para treinos do tipo 'academia'
     */
    @Column(name = "series")
    private Integer series;

    @Column(name = "repeticoes")
    private Integer repeticoes;

    @Column(name = "carga_repeticao")
    private Double cargaRepeticao;

    @Column(name = "carga_total")
    private Double cargaTotal;

    @ElementCollection
    @CollectionTable(
            name = "treino_grupos_musculares",
            joinColumns = @JoinColumn(name = "treino_id")
    )
    @Column(name = "grupo_muscular", length = 100)
    private List<String> gruposMusculares;

    /*
        Campos para treinos do tipo 'Cardio'
     */
    private Double distancia;

    @Column(name = "elevacao_acumulada")
    private Double elevacaoAcumulada;

    @Column(name = "elevacao_maxima")
    private Double elevacaoMaxima;

    @Column(name = "velocidade_media")
    private Double velocidadeMedia;

    @Column(name = "velocidade_maxima")
    private Double velocidadeMaxima;

    @ElementCollection
    @CollectionTable(
            name = "treino_equipamentos",
            joinColumns = @JoinColumn(name = "treino_id")
    )
    @Column(name = "equipamentos", length = 100)
    private List<String> equipamentos;

    @Column(name = "freq_cardiaca_maxima")
    private Double frequenciaCardiacaMaxima;

    @Column(name = "freq_cardiaca_media")
    private Double frequenciaCardiacaMedia;

    // ✅ Factory method para Treino
    public static Treino criarTreino(String descricao, LocalDateTime dataInicial, Boolean ehAgendado,
                                     String observacoes, Usuario usuario, ModalidadeTreino modalidade,
                                     ObjetivoTreino objetivo, IntensidadeTreino intensidade) {
        Treino treino = Treino.builder()
                .modalidade(modalidade)
                .objetivo(objetivo)
                .intensidade(intensidade)
                .build();

        // Setar campos da superclasse
        treino.setDescricao(descricao);
        treino.setDataInicial(dataInicial);
        treino.setEhAgendado(ehAgendado);
        treino.setObservacoes(observacoes);
        treino.setUsuario(usuario);

        return treino;
    }
}