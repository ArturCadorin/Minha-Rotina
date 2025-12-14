package com.rotina.minhaRotina.controller;

import com.rotina.minhaRotina.dto.request.UsuarioRequestDTO;
import com.rotina.minhaRotina.dto.common.MensagemResponseDTO;
import com.rotina.minhaRotina.dto.response.UsuarioResponseDTO;
import com.rotina.minhaRotina.service.UsuarioService;
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
@RequestMapping("usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários do sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ===== Cadastrando um novo usuario =====
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(
            @Parameter(description = "Dados do usuário a ser criado")
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioCriado = usuarioService.criarUsuario(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    // ===== Buscando usuario pelo seu ID =====
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @Parameter(description = "ID do usuário")
            @PathVariable Long id) {

        UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // ===== Buscando todos os usuario =====
    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // ===== Buscando usuario pelo seu email =====
    @Operation(summary = "Buscar usuário por email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(
            @Parameter(description = "Email do usuário")
            @PathVariable String email) {

        UsuarioResponseDTO usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // ===== Buscando usuario pelo seu nome =====
    @Operation(summary = "Buscar usuários por nome")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados")
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(
            @Parameter(description = "Nome (ou parte) para busca")
            @RequestParam String nome) {

        List<UsuarioResponseDTO> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    // ===== Buscando usuario pelo seu cpf =====
    @Operation(summary = "Buscar usuário por cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorCpf(
            @Parameter(description = "CPF do usuário")
            @PathVariable String cpf) {

        UsuarioResponseDTO usuario = usuarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(usuario);
    }

    // ===== Buscando usuarios com tarefas vinculadas =====
    @Operation(summary = "Buscar usuários com tarefas vinculadas")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados")
    @GetMapping("/com-tarefas")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarComTarefas() {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarComTarefas();
        return ResponseEntity.ok(usuarios);
    }

    // ===== Atualizando os dados do usuario =====
    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ou CPF já em uso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário a ser atualizado")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do usuário")
            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // ===== Excluindo um usuario =====
    @Operation(summary = "Excluir um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Usuário possui tarefas vinculadas")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponseDTO> excluirUsuario(
            @Parameter(description = "ID do usuário a ser excluído")
            @PathVariable Long id) {

        usuarioService.excluirUsuario(id);

        MensagemResponseDTO response = new MensagemResponseDTO(
                "Usuário excluído com sucesso",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(response);
    }

    // ===== Contagem de usuarios cadastrados =====
    @Operation(summary = "Contar total de usuários")
    @ApiResponse(responseCode = "200", description = "Total de usuários retornado")
    @GetMapping("/contagem")
    public ResponseEntity<Long> contarUsuarios() {
        long total = usuarioService.contarUsuarios();
        return ResponseEntity.ok(total);
    }
}