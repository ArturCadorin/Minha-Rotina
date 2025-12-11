package com.rotina.minhaRotina.repository;

import com.rotina.minhaRotina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verifica se email já existe
    boolean existsByEmail(String email);

    // Verifica se CPF já existe
    boolean existsByCpf(String cpf);

    // Busca por nome (case insensitive)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Busca por parte do email
    List<Usuario> findByEmailContaining(String email);

    // Busca usuários com tarefas (usando JOIN FETCH)
    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.tarefas WHERE u.id = :id")
    Optional<Usuario> findByIdWithTarefas(@Param("id") Long id);

    // Conta total de usuários
    long count();

    // Busca usuários ordenados por data de criação
    List<Usuario> findAllByOrderByDataCriacaoDesc();

    // Busca usuários ativos (com tarefas)
    @Query("SELECT u FROM Usuario u WHERE SIZE(u.tarefas) > 0")
    List<Usuario> findUsuariosComTarefas();
}