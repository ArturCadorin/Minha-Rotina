package com.rotina.minhaRotina.repository;

import com.rotina.minhaRotina.entity.Estudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudoRepository extends JpaRepository<Estudo, Long> {

    List<Estudo> findByUsuarioId(Long usuarioId);
    List<Estudo> findByConteudoContainingIgnoreCase(String conteudo);
    List<Estudo> findByObjetivoContainingIgnoreCase(String objetivo);
    List<Estudo> findByHorasDiariasGreaterThan(Integer horas);
    List<Estudo> findByHorasTotaisBetween(Integer minHoras, Integer maxHoras);
    List<Estudo> findAllByOrderByHorasTotaisDesc();
    List<Estudo> findByUsuarioIdAndObjetivoContainingIgnoreCase(Long usuarioId, String objetivo);

    // Estatísticas de estudos por usuário
    @Query("SELECT COUNT(e), SUM(e.horasTotais), AVG(e.horasTotais) FROM Estudo e WHERE e.usuario.id = :usuarioId")
    Object[] findEstatisticasByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Busca os estudos com carga horária intensa (> 4 horas diárias)
    @Query("SELECT e FROM Estudo e WHERE e.horasDiarias > 4")
    List<Estudo> findEstudosIntensos();

    // Busca estudos com usuário carregado
    @Query("SELECT e FROM Estudo e JOIN FETCH e.usuario WHERE e.id = :id")
    Optional<Estudo> findByIdWithUsuario(@Param("id") Long id);
}