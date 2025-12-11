package com.rotina.minhaRotina.controller;

import com.rotina.minhaRotina.dto.common.MensagemResponseDTO;
import com.rotina.minhaRotina.dto.response.TarefaResponseDTO;
import com.rotina.minhaRotina.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Operações relacionadas a tarefas gerais")
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(summary = "Buscar tarefa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(
            @Parameter(description = "ID da tarefa")
            @PathVariable Long id) {

        TarefaResponseDTO tarefa = tarefaService.buscarPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @Operation(summary = "Listar todas as tarefas")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada")
    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas() {
        List<TarefaResponseDTO> tarefas = tarefaService.listarTodas();
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @GetMapping("/periodo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorPeriodo(
            @Parameter(description = "Data/hora inicial (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas por usuário e período")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorUsuarioEPeriodo(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId,
            @Parameter(description = "Data/hora inicial (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data/hora final (formato: yyyy-MM-ddTHH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorUsuarioEPeriodo(usuarioId, inicio, fim);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas agendadas")
    @ApiResponse(responseCode = "200", description = "Tarefas agendadas encontradas")
    @GetMapping("/agendadas")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefasAgendadas() {
        List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefasAgendadas();
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas agendadas por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas agendadas encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/agendadas")
    public ResponseEntity<List<TarefaResponseDTO>> buscarTarefasAgendadasPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarTarefasAgendadasPorUsuario(usuarioId);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar próximas tarefas do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Próximas tarefas encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/proximas")
    public ResponseEntity<List<TarefaResponseDTO>> buscarProximasTarefas(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarProximasTarefas(usuarioId);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Buscar tarefas por descrição")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @GetMapping("/buscar")
    public ResponseEntity<List<TarefaResponseDTO>> buscarPorDescricao(
            @Parameter(description = "Descrição (ou parte) para busca")
            @RequestParam String descricao) {

        List<TarefaResponseDTO> tarefas = tarefaService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Excluir uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(
            @Parameter(description = "ID da tarefa a ser excluída")
            @PathVariable Long id) {

        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Contar tarefas por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/contagem")
    public ResponseEntity<Long> contarTarefasPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        long total = tarefaService.contarTarefasPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }
}