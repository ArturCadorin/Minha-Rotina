package com.rotina.minhaRotina.service;

import com.rotina.minhaRotina.dto.request.EstudoRequestDTO;
import com.rotina.minhaRotina.dto.response.EstudoResponseDTO;
import com.rotina.minhaRotina.entity.Estudo;
import com.rotina.minhaRotina.entity.Usuario;
import com.rotina.minhaRotina.mapper.EstudoMapper;
import com.rotina.minhaRotina.mapper.MapperHelper;
import com.rotina.minhaRotina.repository.EstudoRepository;
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
public class EstudoService {

    private final EstudoRepository estudoRepository;
    private final UsuarioService usuarioService;
    private final EstudoMapper estudoMapper;
    private final MapperHelper mapperHelper;

    public EstudoResponseDTO criarEstudo(EstudoRequestDTO estudoRequestDTO) {
        // Validar dados
        validarEstudo(estudoRequestDTO);

        // Buscar usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(estudoRequestDTO.getUsuarioId());

        // Converter DTO para Entity usando MapperHelper
        Estudo estudo = mapperHelper.toEstudoEntity(estudoRequestDTO, usuario);

        // Salvar no banco
        Estudo estudoSalvo = estudoRepository.save(estudo);

        // Retornar DTO de resposta
        return estudoMapper.toDTO(estudoSalvo);
    }

    @Transactional(readOnly = true)
    public EstudoResponseDTO buscarPorId(Long id) {
        Estudo estudo = estudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudo", id));
        return estudoMapper.toDTO(estudo);
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> listarTodos() {
        return estudoRepository.findAll()
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return estudoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarPorConteudo(String conteudo) {
        return estudoRepository.findByConteudoContainingIgnoreCase(conteudo)
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarPorObjetivo(String objetivo) {
        return estudoRepository.findByObjetivoContainingIgnoreCase(objetivo)
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarEstudosIntensos() {
        return estudoRepository.findEstudosIntensos()
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarPorUsuarioEObjetivo(Long usuarioId, String objetivo) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return estudoRepository.findByUsuarioIdAndObjetivoContainingIgnoreCase(usuarioId, objetivo)
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EstudoResponseDTO atualizarEstudo(Long id, EstudoRequestDTO estudoRequestDTO) {
        // Validar dados
        validarEstudo(estudoRequestDTO);

        // Buscar estudo existente
        Estudo estudoExistente = estudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudo", id));

        // Buscar usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(estudoRequestDTO.getUsuarioId());

        // Atualizar entidade usando MapperHelper
        mapperHelper.updateEstudoFromDTO(estudoRequestDTO, estudoExistente, usuario);

        // Salvar atualização
        Estudo estudoAtualizado = estudoRepository.save(estudoExistente);

        return estudoMapper.toDTO(estudoAtualizado);
    }

    public void excluirEstudo(Long id) {
        // Verificar se estudo existe
        Estudo estudo = estudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudo", id));

        estudoRepository.delete(estudo);
    }

    @Transactional(readOnly = true)
    public Object[] obterEstatisticasPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return estudoRepository.findEstatisticasByUsuarioId(usuarioId);
    }

    private void validarEstudo(EstudoRequestDTO estudoRequestDTO) {
        // Validar horas diárias vs horas totais
        if (estudoRequestDTO.getHorasDiarias() != null && estudoRequestDTO.getHorasTotais() != null) {
            if (estudoRequestDTO.getHorasDiarias() > estudoRequestDTO.getHorasTotais()) {
                throw new BusinessException("Horas diárias não podem ser maiores que horas totais");
            }
        }

        // Validar datas
        if (estudoRequestDTO.getDataFinal() != null &&
                estudoRequestDTO.getDataInicial() != null &&
                estudoRequestDTO.getDataFinal().isBefore(estudoRequestDTO.getDataInicial())) {
            throw new BusinessException("Data final não pode ser anterior à data inicial");
        }
    }
}