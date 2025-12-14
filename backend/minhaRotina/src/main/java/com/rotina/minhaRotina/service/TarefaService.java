package com.rotina.minhaRotina.service;

import com.rotina.minhaRotina.dto.response.TarefaResponseDTO;
import com.rotina.minhaRotina.entity.Tarefa;
import com.rotina.minhaRotina.mapper.TarefaMapper;
import com.rotina.minhaRotina.repository.TarefaRepository;
import com.rotina.minhaRotina.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final UsuarioService usuarioService;

    // ===== Buscando pelo ID =====
    @Transactional(readOnly = true)
    public TarefaResponseDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));
        return tarefaMapper.toDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public Tarefa buscarEntidadePorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));
    }

    // ===== Busca todas tarefas =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> listarTodas() {
        return tarefaRepository.findAll()
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca por usuario =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return tarefaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca tarefa por periodo =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return tarefaRepository.findByDataInicialBetween(inicio, fim)
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca tarefa por usuario e periodo =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorUsuarioEPeriodo(Long usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return tarefaRepository.findByUsuarioIdAndDataInicialBetween(usuarioId, inicio, fim)
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca tarefas agendadas =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarTarefasAgendadas() {
        return tarefaRepository.findByEhAgendadoTrue()
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca tarefas agendadas de um usuario =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarTarefasAgendadasPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return tarefaRepository.findByUsuarioIdAndEhAgendadoTrue(usuarioId)
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca as proximas tarefas cadastradas =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarProximasTarefas(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return tarefaRepository.findProximasTarefas(usuarioId, LocalDateTime.now())
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Busca tarefa pela descricao =====
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscarPorDescricao(String descricao) {
        return tarefaRepository.findByDescricaoContainingIgnoreCase(descricao)
                .stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Exclui uma tarefa =====
    public void excluirTarefa(Long id) {
        // Verificar se tarefa existe
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));

        tarefaRepository.delete(tarefa);
    }

    // ===== Contagem de tarefas por usuario =====
    @Transactional(readOnly = true)
    public long contarTarefasPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return tarefaRepository.countByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public boolean existeTarefa(Long id) {
        return tarefaRepository.existsById(id);
    }
}