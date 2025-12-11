package com.rotina.minhaRotina.controller;

import com.rotina.minhaRotina.dto.request.TreinoRequestDTO;
import com.rotina.minhaRotina.dto.common.MensagemResponseDTO;
import com.rotina.minhaRotina.dto.response.TreinoResponseDTO;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.service.TreinoService;
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
@RequestMapping("treinos")
@RequiredArgsConstructor
@Tag(name = "Treinos", description = "Operações relacionadas a treinos")
public class TreinoController {

    private final TreinoService treinoService;

    @Operation(summary = "Criar um novo treino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Treino criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<TreinoResponseDTO> criarTreino(
            @Parameter(description = "Dados do treino a ser criado")
            @RequestBody @Valid TreinoRequestDTO treinoRequestDTO) {

        TreinoResponseDTO treinoCriado = treinoService.criarTreino(treinoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(treinoCriado);
    }

    @Operation(summary = "Buscar treino por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino encontrado"),
            @ApiResponse(responseCode = "404", description = "Treino não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> buscarPorId(
            @Parameter(description = "ID do treino")
            @PathVariable Long id) {

        TreinoResponseDTO treino = treinoService.buscarPorId(id);
        return ResponseEntity.ok(treino);
    }

    @Operation(summary = "Listar todos os treinos")
    @ApiResponse(responseCode = "200", description = "Lista de treinos retornada")
    @GetMapping
    public ResponseEntity<List<TreinoResponseDTO>> listarTodos() {
        List<TreinoResponseDTO> treinos = treinoService.listarTodos();
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treinos encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TreinoResponseDTO>> buscarPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<TreinoResponseDTO> treinos = treinoService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos por modalidade")
    @ApiResponse(responseCode = "200", description = "Treinos encontrados")
    @GetMapping("/modalidade/{modalidade}")
    public ResponseEntity<List<TreinoResponseDTO>> buscarPorModalidade(
            @Parameter(description = "Modalidade do treino")
            @PathVariable ModalidadeTreino modalidade) {

        List<TreinoResponseDTO> treinos = treinoService.buscarPorModalidade(modalidade);
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos de cardio")
    @ApiResponse(responseCode = "200", description = "Treinos de cardio encontrados")
    @GetMapping("/cardio")
    public ResponseEntity<List<TreinoResponseDTO>> buscarTreinosCardio() {
        List<TreinoResponseDTO> treinos = treinoService.buscarTreinosCardio();
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos de força")
    @ApiResponse(responseCode = "200", description = "Treinos de força encontrados")
    @GetMapping("/forca")
    public ResponseEntity<List<TreinoResponseDTO>> buscarTreinosForca() {
        List<TreinoResponseDTO> treinos = treinoService.buscarTreinosForca();
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos por grupo muscular")
    @ApiResponse(responseCode = "200", description = "Treinos encontrados")
    @GetMapping("/grupo-muscular")
    public ResponseEntity<List<TreinoResponseDTO>> buscarPorGrupoMuscular(
            @Parameter(description = "Grupo muscular para busca")
            @RequestParam String grupoMuscular) {

        List<TreinoResponseDTO> treinos = treinoService.buscarPorGrupoMuscular(grupoMuscular);
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Buscar treinos por equipamento")
    @ApiResponse(responseCode = "200", description = "Treinos encontrados")
    @GetMapping("/equipamento")
    public ResponseEntity<List<TreinoResponseDTO>> buscarPorEquipamento(
            @Parameter(description = "Equipamento para busca")
            @RequestParam String equipamento) {

        List<TreinoResponseDTO> treinos = treinoService.buscarPorEquipamento(equipamento);
        return ResponseEntity.ok(treinos);
    }

    @Operation(summary = "Atualizar um treino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Treino ou usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TreinoResponseDTO> atualizarTreino(
            @Parameter(description = "ID do treino a ser atualizado")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do treino")
            @RequestBody @Valid TreinoRequestDTO treinoRequestDTO) {

        TreinoResponseDTO treinoAtualizado = treinoService.atualizarTreino(id, treinoRequestDTO);
        return ResponseEntity.ok(treinoAtualizado);
    }

    @Operation(summary = "Excluir um treino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Treino excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Treino não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponseDTO> excluirTreino(
            @Parameter(description = "ID do treino a ser excluído")
            @PathVariable Long id) {

        treinoService.excluirTreino(id);

        MensagemResponseDTO response = new MensagemResponseDTO(
                "Treino excluído com sucesso",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter estatísticas de treinos por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas retornadas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/estatisticas")
    public ResponseEntity<Object[]> obterEstatisticasPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        Object[] estatisticas = treinoService.obterEstatisticasPorUsuario(usuarioId);
        return ResponseEntity.ok(estatisticas);
    }

    @Operation(summary = "Obter distribuição de modalidades por usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distribuição retornada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/usuario/{usuarioId}/distribuicao-modalidades")
    public ResponseEntity<List<Object[]>> obterDistribuicaoModalidadesPorUsuario(
            @Parameter(description = "ID do usuário")
            @PathVariable Long usuarioId) {

        List<Object[]> distribuicao = treinoService.obterDistribuicaoModalidadesPorUsuario(usuarioId);
        return ResponseEntity.ok(distribuicao);
    }
}