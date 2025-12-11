package com.rotina.minhaRotina.controller;

import com.rotina.minhaRotina.dto.request.EstudoRequestDTO;
import com.rotina.minhaRotina.dto.response.EstudoResponseDTO;
import com.rotina.minhaRotina.dto.common.MensagemResponseDTO;
import com.rotina.minhaRotina.service.EstudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("estudos")
@RequiredArgsConstructor
@Tag(name = "Estudos", description = "Operações relacionadas a estudos")
public class EstudoController {

    private final EstudoService estudoService;

    @Operation(summary = "Criar um novo estudo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<EstudoResponseDTO> criarEstudo(
            @Parameter(description = "Dados do estudo a ser criado")
            @RequestBody @Valid EstudoRequestDTO estudoRequestDTO) {

        EstudoResponseDTO estudoCriado = estudoService.criarEstudo(estudoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(estudoCriado);
    }

    @Operation(summary = "Buscar estudo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudo encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudoResponseDTO> buscarPorId(
            @Parameter(description = "ID do estudo")
            @PathVariable Long id) {

        EstudoResponseDTO estudo = estudoService.buscarPorId(id);
        return ResponseEntity.ok(estudo);
    }

    @Operation(summary = "Listar todos os estudos")
    @ApiResponse(responseCode = "200", description = "Lista de estudos retornada")
    @GetMapping
    public ResponseEntity<List<EstudoResponseDTO>> listarTodos() {
        List<EstudoResponseDTO> estudos = estudoService.listarTodos();
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Buscar estudos por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudos encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EstudoResponseDTO>> buscarPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<EstudoResponseDTO> estudos = estudoService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Buscar estudos por conteúdo")
    @ApiResponse(responseCode = "200", description = "Estudos encontrados")
    @GetMapping("/conteudo")
    public ResponseEntity<List<EstudoResponseDTO>> buscarPorConteudo(
            @Parameter(description = "Conteúdo (ou parte) para busca")
            @RequestParam String conteudo) {

        List<EstudoResponseDTO> estudos = estudoService.buscarPorConteudo(conteudo);
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Buscar estudos por objetivo")
    @ApiResponse(responseCode = "200", description = "Estudos encontrados")
    @GetMapping("/objetivo")
    public ResponseEntity<List<EstudoResponseDTO>> buscarPorObjetivo(
            @Parameter(description = "Objetivo (ou parte) para busca")
            @RequestParam String objetivo) {

        List<EstudoResponseDTO> estudos = estudoService.buscarPorObjetivo(objetivo);
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Buscar estudos intensos (mais de 4 horas diárias)")
    @ApiResponse(responseCode = "200", description = "Estudos intensos encontrados")
    @GetMapping("/intensos")
    public ResponseEntity<List<EstudoResponseDTO>> buscarEstudosIntensos() {
        List<EstudoResponseDTO> estudos = estudoService.buscarEstudosIntensos();
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Buscar estudos por usuário e objetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudos encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/objetivo")
    public ResponseEntity<List<EstudoResponseDTO>> buscarPorUsuarioEObjetivo(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId,
            @Parameter(description = "Objetivo (ou parte) para busca")
            @RequestParam String objetivo) {

        List<EstudoResponseDTO> estudos = estudoService.buscarPorUsuarioEObjetivo(usuarioId, objetivo);
        return ResponseEntity.ok(estudos);
    }

    @Operation(summary = "Atualizar um estudo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estudo ou usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudoResponseDTO> atualizarEstudo(
            @Parameter(description = "ID do estudo a ser atualizado")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do estudo")
            @RequestBody @Valid EstudoRequestDTO estudoRequestDTO) {

        EstudoResponseDTO estudoAtualizado = estudoService.atualizarEstudo(id, estudoRequestDTO);
        return ResponseEntity.ok(estudoAtualizado);
    }

    @Operation(summary = "Excluir um estudo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponseDTO> excluirEstudo(
            @Parameter(description = "ID do estudo a ser excluído")
            @PathVariable Long id) {

        estudoService.excluirEstudo(id);

        MensagemResponseDTO response = new MensagemResponseDTO(
                "Estudo excluído com sucesso",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter estatísticas de estudos por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas retornadas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/estatisticas")
    public ResponseEntity<Object[]> obterEstatisticasPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        Object[] estatisticas = estudoService.obterEstatisticasPorUsuario(usuarioId);
        return ResponseEntity.ok(estatisticas);
    }
}