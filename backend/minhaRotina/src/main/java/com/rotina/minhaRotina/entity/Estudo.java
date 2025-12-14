package com.rotina.minhaRotina.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ESTUDO")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudo extends Tarefa {

    /*
        Dados do básicos do estudo
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Column(nullable = false, length = 100)
    private String objetivo;

    @Column(name = "horas_totais")
    private Integer horasTotais;

    @Column(name = "horas_diarias")
    private Integer horasDiarias;

    @Column(name = "eh_simulado")
    private Boolean ehSimulado;

    @Column(name = "quantidade_questoes")
    private Integer quantidadeQuestoes;

    @Column(name = "nota_final_simulado")
    private Double notaFinalSimulado;

    // Factory method - criando tarefa tipo estudo
    public static Estudo criarEstudo(String descricao, LocalDateTime dataInicial, Boolean ehAgendado,
                                     Integer horasTotais, Integer horasDiarias, Boolean ehSimulado,
                                     Integer quantidadeQuestoes, Double notaFinalSimulado,
                                     String observacoes, Usuario usuario, String conteudo, String objetivo) {
        Estudo estudo = Estudo.builder()
                .conteudo(conteudo)
                .objetivo(objetivo)
                .horasTotais(horasTotais)
                .horasDiarias(horasDiarias)
                .ehSimulado(ehSimulado)
                .quantidadeQuestoes(quantidadeQuestoes)
                .notaFinalSimulado(notaFinalSimulado)
                .build();

        // Setando os campos da superclasse
        estudo.setDescricao(descricao);
        estudo.setDataInicial(dataInicial);
        estudo.setEhAgendado(ehAgendado);
        estudo.setObservacoes(observacoes);
        estudo.setUsuario(usuario);

        return estudo;
    }
}