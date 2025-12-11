package com.rotina.minhaRotina.repository;

import com.rotina.minhaRotina.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Busca todas as tarefas de um usuário
    List<Tarefa> findByUsuarioId(Long usuarioId);

    // Busca tarefas por período
    List<Tarefa> findByDataInicialBetween(LocalDateTime inicio, LocalDateTime fim);

    // Busca tarefas agendadas
    List<Tarefa> findByEhAgendadoTrue();

    // Busca tarefas não agendadas
    List<Tarefa> findByEhAgendadoFalse();

    // Busca tarefas por usuário e período
    List<Tarefa> findByUsuarioIdAndDataInicialBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);

    // Busca tarefas agendadas de um usuário
    List<Tarefa> findByUsuarioIdAndEhAgendadoTrue(Long usuarioId);

    // Busca tarefas por descrição (case insensitive)
    List<Tarefa> findByDescricaoContainingIgnoreCase(String descricao);

    // Busca tarefas com observações
    List<Tarefa> findByObservacoesIsNotNull();

    // Busca tarefas sem observações
    List<Tarefa> findByObservacoesIsNull();

    // Busca próxima tarefa agendada do usuário
    @Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND t.dataInicial > :agora ORDER BY t.dataInicial ASC")
    List<Tarefa> findProximasTarefas(@Param("usuarioId") Long usuarioId, @Param("agora") LocalDateTime agora);

    // Conta tarefas por usuário
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.usuario.id = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Busca tarefas com usuário carregado (evita Lazy Loading)
    @Query("SELECT t FROM Tarefa t JOIN FETCH t.usuario WHERE t.id = :id")
    Optional<Tarefa> findByIdWithUsuario(@Param("id") Long id);
}