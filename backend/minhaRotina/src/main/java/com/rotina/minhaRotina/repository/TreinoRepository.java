package com.rotina.minhaRotina.repository;

import com.rotina.minhaRotina.entity.Treino;
import com.rotina.minhaRotina.enums.IntensidadeTreino;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.enums.ObjetivoTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    List<Treino> findByUsuarioId(Long usuarioId);
    List<Treino> findByModalidade(ModalidadeTreino modalidade);
    List<Treino> findByIntensidade(IntensidadeTreino intensidade);
    List<Treino> findByObjetivo(ObjetivoTreino objetivo);
    List<Treino> findByDistanciaGreaterThan(Double distancia);
    List<Treino> findBySeries(Integer series);
    List<Treino> findByModalidadeAndIntensidade(ModalidadeTreino modalidade, IntensidadeTreino intensidade);
    List<Treino> findByDistanciaIsNotNull();
    List<Treino> findBySeriesIsNotNull();
    List<Treino> findAllByOrderByHorasTotaisDesc();

    // Busca treinos com velocidade média maior que
    List<Treino> findByVelocidadeMediaGreaterThan(Double velocidade);
    // Busca treinos intensos (alta intensidade)
    List<Treino> findByIntensidadeAndHorasTotaisGreaterThan(IntensidadeTreino intensidade, Long horasMinimas);

    // Estatísticas de treinos por usuário
    @Query("SELECT COUNT(t), SUM(t.horasTotais), AVG(t.horasTotais) FROM Treino t WHERE t.usuario.id = :usuarioId")
    Object[] findEstatisticasByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Busca treinos por grupos musculares
    @Query("SELECT t FROM Treino t JOIN t.gruposMusculares g WHERE g = :grupoMuscular")
    List<Treino> findByGrupoMuscular(@Param("grupoMuscular") String grupoMuscular);

    // Busca treinos por equipamento
    @Query("SELECT t FROM Treino t JOIN t.equipamentos e WHERE e = :equipamento")
    List<Treino> findByEquipamento(@Param("equipamento") String equipamento);

    // Busca treinos com usuário carregado
    @Query("SELECT t FROM Treino t JOIN FETCH t.usuario WHERE t.id = :id")
    Optional<Treino> findByIdWithUsuario(@Param("id") Long id);

    // Distribuição de modalidades por usuário
    @Query("SELECT t.modalidade, COUNT(t) FROM Treino t WHERE t.usuario.id = :usuarioId GROUP BY t.modalidade")
    List<Object[]> findDistribuicaoModalidadesByUsuarioId(@Param("usuarioId") Long usuarioId);
}