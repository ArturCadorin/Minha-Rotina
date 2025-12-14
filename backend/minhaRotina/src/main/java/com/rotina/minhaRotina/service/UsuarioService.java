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

    // ===== Criando um usuario novo =====
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        // Suspendemos o cadastro caso o email ou o CPF ja estejam vinculados ha alguma conta
        if (usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + usuarioRequestDTO.getEmail());
        }
        if (usuarioRepository.existsByCpf(usuarioRequestDTO.getCpf())) {
            throw new BusinessException("CPF já cadastrado: " + usuarioRequestDTO.getCpf());
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    // ===== Atualizando usuario =====
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        // Validando o email e o cpf do usuario
        if (!usuarioExistente.getEmail().equals(usuarioRequestDTO.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail())) {
            throw new BusinessException("Email já está em uso: " + usuarioRequestDTO.getEmail());
        }
        if (!usuarioExistente.getCpf().equals(usuarioRequestDTO.getCpf()) &&
                usuarioRepository.existsByCpf(usuarioRequestDTO.getCpf())) {
            throw new BusinessException("CPF já está em uso: " + usuarioRequestDTO.getCpf());
        }

        // Tratando o IMC do usuario, conforme altura e peso cadastrados
        if(usuarioRequestDTO.getAltura() != null && usuarioRequestDTO.getAltura() > 0 &&
                usuarioRequestDTO.getPeso() != null && usuarioRequestDTO.getPeso() > 0){
            usuarioRequestDTO.setImc(usuarioRequestDTO.getPeso() / (usuarioRequestDTO.getAltura() * usuarioRequestDTO.getAltura()));
        }

        usuarioMapper.updateEntityFromDTO(usuarioRequestDTO, usuarioExistente);
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    // ===== Deletando o usuario =====
    public void excluirUsuario(Long id) {
        // Verifica se usuário existe
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));

        // Suspende exclusão se o usuario já tiver tarefas cadastradas
        if (!usuario.getTarefas().isEmpty()) {
            throw new BusinessException("Não é possível excluir usuário com tarefas vinculadas");
        }

        usuarioRepository.delete(usuario);
    }

    // ===== Metodos para transações(conversão) de dados =====
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

    // Busca o usuario conforme o email
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
        return usuarioMapper.toDTO(usuario);
    }

    // Busca o usuario conforme o nome
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Busca o usuario conforme o cpf
    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com cpf: " + cpf));
        return usuarioMapper.toDTO(usuario);
    }

    // Busca usuarios com tarefas vinculadas
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> buscarComTarefas() {
        return usuarioRepository.findUsuariosComTarefas()
                .stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Verificando se existe usuario =====
    @Transactional(readOnly = true)
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }

    // ===== Contagem de usuarios =====
    @Transactional(readOnly = true)
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
}
