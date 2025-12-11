package com.rotina.minhaRotina.service;

import com.rotina.minhaRotina.dto.request.UsuarioRequestDTO;
import com.rotina.minhaRotina.dto.response.UsuarioResponseDTO;
import com.rotina.minhaRotina.entity.Usuario;
import com.rotina.minhaRotina.mapper.UsuarioMapper;
import com.rotina.minhaRotina.repository.UsuarioRepository;
import com.rotina.minhaRotina.service.exception.BusinessException;
import com.rotina.minhaRotina.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        // Validar email único
        if (usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + usuarioRequestDTO.getEmail());
        }

        // Validar CPF único
        if (usuarioRepository.existsByCpf(usuarioRequestDTO.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + usuarioRequestDTO.getCpf());
        }

        // Converter DTO para Entity
        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);

        // Salvar no banco
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Retornar DTO de resposta
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        // Verificar se usuário existe
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        // Verificar se novo email já existe (se foi alterado)
        if (!usuarioExistente.getEmail().equals(usuarioRequestDTO.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())) {
            throw new BusinessException("Email já está em uso: " + usuarioRequestDTO.getEmail());
        }

        // Verificar se novo CPF já existe (se foi alterado)
        if (!usuarioExistente.getCpf().equals(usuarioRequestDTO.getCpf()) &&
                usuarioRepository.existsByCpf(usuarioRequestDTO.getCpf())) {
            throw new BusinessException("CPF já está em uso: " + usuarioRequestDTO.getCpf());
        }

        // Atualizar entidade
        usuarioMapper.updateEntityFromDTO(usuarioRequestDTO, usuarioExistente);

        // Salvar atualização
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    public void excluirUsuario(Long id) {
        // Verificar se usuário existe
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        // Verificar se usuário tem tarefas antes de excluir
        if (!usuario.getTarefas().isEmpty()) {
            throw new BusinessException("Não é possível excluir usuário com tarefas vinculadas");
        }

        usuarioRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
}
