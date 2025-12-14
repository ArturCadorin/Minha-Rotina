package com.rotina.minhaRotina.repository;

import com.rotina.minhaRotina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    long count();

    // validação para o email e o cpf (campos unicos)
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT u FROM Usuario u WHERE SIZE(u.tarefas) > 0")
    List<Usuario> findUsuariosComTarefas();

}