package com.rotina.minhaRotina.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "tipo_tarefa",
        discriminatorType = DiscriminatorType.STRING,
        length = 20
)
@Table(name = "tarefas")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Dados básicos da tarefa
     */
    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false, name = "data_inicial")
    private LocalDateTime dataInicial;

    @Column(name = "data_final")
    private LocalDateTime dataFinal;

    @Column(nullable = false, name = "eh_agendado")
    private Boolean ehAgendado;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({
            "email", "cpf", "rg", "dataNascimento", "sexo",
            "telefone", "dataCriacao", "dataAtualizacao"
    })
    private Usuario usuario;

    /*
        Dados para auditoria do sistema
     */
    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}