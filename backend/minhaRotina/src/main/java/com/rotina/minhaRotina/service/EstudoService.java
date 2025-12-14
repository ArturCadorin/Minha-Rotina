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

    // ===== Criando um estudo =====
    public EstudoResponseDTO criarEstudo(EstudoRequestDTO estudoRequestDTO) {

        validarEstudo(estudoRequestDTO);

        Usuario usuario = usuarioService.buscarEntidadePorId(estudoRequestDTO.getUsuarioId());
        Estudo estudo = mapperHelper.toEstudoEntity(estudoRequestDTO, usuario);
        Estudo estudoSalvo = estudoRepository.save(estudo);

        return estudoMapper.toDTO(estudoSalvo);
    }

    // ===== Atualizando um estudo =====
    public EstudoResponseDTO atualizarEstudo(Long id, EstudoRequestDTO estudoRequestDTO) {

        validarEstudo(estudoRequestDTO);

        Estudo estudoExistente = estudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudo", id));

        Usuario usuario = usuarioService.buscarEntidadePorId(estudoRequestDTO.getUsuarioId());
        mapperHelper.updateEstudoFromDTO(estudoRequestDTO, estudoExistente, usuario);
        Estudo estudoAtualizado = estudoRepository.save(estudoExistente);

        return estudoMapper.toDTO(estudoAtualizado);
    }

    // ===== Excluindo um estudo =====
    public void excluirEstudo(Long id) {

        Estudo estudo = estudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudo", id));

        estudoRepository.delete(estudo);
    }

    // ===== Metodos para transações(conversão) de dados =====
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
        return estudoRepository.    findEstudosIntensos()
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstudoResponseDTO> buscarPorUsuarioEObjetivo(Long usuarioId, String objetivo) {

        usuarioService.existeUsuario(usuarioId);

        return estudoRepository.findByUsuarioIdAndObjetivoContainingIgnoreCase(usuarioId, objetivo)
                .stream()
                .map(estudoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Object[] obterEstatisticasPorUsuario(Long usuarioId) {

        usuarioService.existeUsuario(usuarioId);
        return estudoRepository.findEstatisticasByUsuarioId(usuarioId);
    }

    // ===== Metodo para validacao dos dados =====
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