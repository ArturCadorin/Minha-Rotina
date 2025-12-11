package com.rotina.minhaRotina.mapper;

import com.rotina.minhaRotina.dto.request.*;
import com.rotina.minhaRotina.entity.*;
import org.springframework.stereotype.Component;

@Component
public class MapperHelper {

    private final TarefaMapper tarefaMapper;
    private final UsuarioMapper usuarioMapper;

    public MapperHelper(TarefaMapper tarefaMapper, UsuarioMapper usuarioMapper) {
        this.tarefaMapper = tarefaMapper;
        this.usuarioMapper = usuarioMapper;
    }

    // ✅ Converter EstudoRequestDTO para Estudo (manual)
    public Estudo toEstudoEntity(EstudoRequestDTO dto, Usuario usuario) {
        Estudo estudo = new Estudo();

        // Campos da Tarefa
        estudo.setDescricao(dto.getDescricao());
        estudo.setDataInicial(dto.getDataInicial());
        estudo.setDataFinal(dto.getDataFinal());
        estudo.setEhAgendado(dto.getEhAgendado());
        estudo.setObservacoes(dto.getObservacoes());
        estudo.setUsuario(usuario);

        // Campos específicos do Estudo
        estudo.setConteudo(dto.getConteudo());
        estudo.setObjetivo(dto.getObjetivo());
        estudo.setHorasTotais(dto.getHorasTotais());
        estudo.setHorasDiarias(dto.getHorasDiarias());

        return estudo;
    }

    // ✅ Converter TreinoRequestDTO para Treino (manual)
    public Treino toTreinoEntity(TreinoRequestDTO dto, Usuario usuario) {
        Treino treino = new Treino();

        // Campos da Tarefa
        treino.setDescricao(dto.getDescricao());
        treino.setDataInicial(dto.getDataInicial());
        treino.setDataFinal(dto.getDataFinal());
        treino.setEhAgendado(dto.getEhAgendado());
        treino.setObservacoes(dto.getObservacoes());
        treino.setUsuario(usuario);

        // Campos específicos do Treino
        treino.setObjetivo(dto.getObjetivo());
        treino.setIntensidade(dto.getIntensidade());
        treino.setModalidade(dto.getModalidade());
        treino.setHorasTotais(dto.getHorasTotais());
        treino.setSeries(dto.getSeries());
        treino.setRepeticoes(dto.getRepeticoes());
        treino.setCargaRepeticao(dto.getCargaRepeticao());
        treino.setGruposMusculares(dto.getGruposMusculares());
        treino.setDistancia(dto.getDistancia());
        treino.setElevacaoAcumulada(dto.getElevacaoAcumulada());
        treino.setElevacaoMaxima(dto.getElevacaoMaxima());
        treino.setVelocidadeMedia(dto.getVelocidadeMedia());
        treino.setVelocidadeMaxima(dto.getVelocidadeMaxima());
        treino.setEquipamentos(dto.getEquipamentos());
        treino.setFrequenciaCardiacaMaxima(dto.getFrequenciaCardiacaMaxima());
        treino.setFrequenciaCardiacaMedia(dto.getFrequenciaCardiacaMedia());

        // Calcular carga total
        if (treino.getSeries() != null && treino.getRepeticoes() != null && treino.getCargaRepeticao() != null) {
            treino.setCargaTotal(treino.getSeries() * treino.getRepeticoes() * treino.getCargaRepeticao());
        }

        return treino;
    }

    // ✅ Atualizar Estudo a partir do DTO
    public void updateEstudoFromDTO(EstudoRequestDTO dto, Estudo estudo, Usuario usuario) {
        // Campos da Tarefa
        estudo.setDescricao(dto.getDescricao());
        estudo.setDataInicial(dto.getDataInicial());
        estudo.setDataFinal(dto.getDataFinal());
        estudo.setEhAgendado(dto.getEhAgendado());
        estudo.setObservacoes(dto.getObservacoes());
        estudo.setUsuario(usuario);

        // Campos específicos do Estudo
        estudo.setConteudo(dto.getConteudo());
        estudo.setObjetivo(dto.getObjetivo());
        estudo.setHorasTotais(dto.getHorasTotais());
        estudo.setHorasDiarias(dto.getHorasDiarias());
    }

    // ✅ Atualizar Treino a partir do DTO
    public void updateTreinoFromDTO(TreinoRequestDTO dto, Treino treino, Usuario usuario) {
        // Campos da Tarefa
        treino.setDescricao(dto.getDescricao());
        treino.setDataInicial(dto.getDataInicial());
        treino.setDataFinal(dto.getDataFinal());
        treino.setEhAgendado(dto.getEhAgendado());
        treino.setObservacoes(dto.getObservacoes());
        treino.setUsuario(usuario);

        // Campos específicos do Treino
        treino.setObjetivo(dto.getObjetivo());
        treino.setIntensidade(dto.getIntensidade());
        treino.setModalidade(dto.getModalidade());
        treino.setHorasTotais(dto.getHorasTotais());
        treino.setSeries(dto.getSeries());
        treino.setRepeticoes(dto.getRepeticoes());
        treino.setCargaRepeticao(dto.getCargaRepeticao());
        treino.setGruposMusculares(dto.getGruposMusculares());
        treino.setDistancia(dto.getDistancia());
        treino.setElevacaoAcumulada(dto.getElevacaoAcumulada());
        treino.setElevacaoMaxima(dto.getElevacaoMaxima());
        treino.setVelocidadeMedia(dto.getVelocidadeMedia());
        treino.setVelocidadeMaxima(dto.getVelocidadeMaxima());
        treino.setEquipamentos(dto.getEquipamentos());
        treino.setFrequenciaCardiacaMaxima(dto.getFrequenciaCardiacaMaxima());
        treino.setFrequenciaCardiacaMedia(dto.getFrequenciaCardiacaMedia());

        // Calcular carga total
        if (treino.getSeries() != null && treino.getRepeticoes() != null && treino.getCargaRepeticao() != null) {
            treino.setCargaTotal(treino.getSeries() * treino.getRepeticoes() * treino.getCargaRepeticao());
        }
    }
}