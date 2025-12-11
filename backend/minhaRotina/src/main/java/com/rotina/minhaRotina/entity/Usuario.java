package com.rotina.minhaRotina.entity;

import com.rotina.minhaRotina.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString(exclude = {"senha", "tarefas"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Dados básicos do usuário do sistema
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Sexo sexo;

    @Column(length = 20)
    private String telefone;

    /*
        Dados atribuidos ao usuário
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Tarefa> tarefas = new ArrayList<>();

    /*
        Dados para auditoria do sistema
     */
    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // factory method - criando usuario simples
    public static Usuario criarUsuarioBasico(String email, String senha, String nome, String cpf) {
        return Usuario.builder()
                .email(email)
                .senha(senha)
                .nome(nome)
                .cpf(cpf)
                .build();
    }

    // factory method - criando usuario completo
    public static Usuario criarUsuarioCompleto(String email, String senha, String nome, String cpf,
                                               LocalDate dataNascimento, Sexo sexo, String telefone) {
        return Usuario.builder()
                .email(email)
                .senha(senha)
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(dataNascimento)
                .sexo(sexo)
                .telefone(telefone)
                .build();
    }
}