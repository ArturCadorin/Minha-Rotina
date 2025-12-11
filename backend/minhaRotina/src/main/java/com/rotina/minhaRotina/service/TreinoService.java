package com.rotina.minhaRotina.service;

import com.rotina.minhaRotina.dto.request.TreinoRequestDTO;
import com.rotina.minhaRotina.dto.response.TreinoResponseDTO;
import com.rotina.minhaRotina.entity.Treino;
import com.rotina.minhaRotina.entity.Usuario;
import com.rotina.minhaRotina.enums.ModalidadeTreino;
import com.rotina.minhaRotina.mapper.MapperHelper;
import com.rotina.minhaRotina.mapper.TreinoMapper;
import com.rotina.minhaRotina.repository.TreinoRepository;
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
public class TreinoService {

    private final TreinoRepository treinoRepository;
    private final UsuarioService usuarioService;
    private final TreinoMapper treinoMapper;
    private final MapperHelper mapperHelper;

    public TreinoResponseDTO criarTreino(TreinoRequestDTO treinoRequestDTO) {
        // Validar dados
        validarTreino(treinoRequestDTO);

        // Buscar usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(treinoRequestDTO.getUsuarioId());

        // Converter DTO para Entity usando MapperHelper
        Treino treino = mapperHelper.toTreinoEntity(treinoRequestDTO, usuario);

        // Salvar no banco
        Treino treinoSalvo = treinoRepository.save(treino);

        // Retornar DTO de resposta
        return treinoMapper.toDTO(treinoSalvo);
    }

    @Transactional(readOnly = true)
    public TreinoResponseDTO buscarPorId(Long id) {
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino", id));
        return treinoMapper.toDTO(treino);
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> listarTodos() {
        return treinoRepository.findAll()
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return treinoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarPorModalidade(ModalidadeTreino modalidade) {
        return treinoRepository.findByModalidade(modalidade)
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarTreinosCardio() {
        return treinoRepository.findByDistanciaIsNotNull()
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarTreinosForca() {
        return treinoRepository.findBySeriesIsNotNull()
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarPorGrupoMuscular(String grupoMuscular) {
        return treinoRepository.findByGrupoMuscular(grupoMuscular)
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreinoResponseDTO> buscarPorEquipamento(String equipamento) {
        return treinoRepository.findByEquipamento(equipamento)
                .stream()
                .map(treinoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TreinoResponseDTO atualizarTreino(Long id, TreinoRequestDTO treinoRequestDTO) {
        // Validar dados
        validarTreino(treinoRequestDTO);

        // Buscar treino existente
        Treino treinoExistente = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino", id));

        // Buscar usuário
        Usuario usuario = usuarioService.buscarEntidadePorId(treinoRequestDTO.getUsuarioId());

        // Atualizar entidade usando MapperHelper
        mapperHelper.updateTreinoFromDTO(treinoRequestDTO, treinoExistente, usuario);

        // Salvar atualização
        Treino treinoAtualizado = treinoRepository.save(treinoExistente);

        return treinoMapper.toDTO(treinoAtualizado);
    }

    public void excluirTreino(Long id) {
        // Verificar se treino existe
        Treino treino = treinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treino", id));

        treinoRepository.delete(treino);
    }

    @Transactional(readOnly = true)
    public Object[] obterEstatisticasPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return treinoRepository.findEstatisticasByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Object[]> obterDistribuicaoModalidadesPorUsuario(Long usuarioId) {
        // Verificar se usuário existe
        usuarioService.existeUsuario(usuarioId);

        return treinoRepository.findDistribuicaoModalidadesByUsuarioId(usuarioId);
    }

    private void validarTreino(TreinoRequestDTO treinoRequestDTO) {
        // Validar datas
        if (treinoRequestDTO.getDataFinal() != null &&
                treinoRequestDTO.getDataInicial() != null &&
                treinoRequestDTO.getDataFinal().isBefore(treinoRequestDTO.getDataInicial())) {
            throw new BusinessException("Data final não pode ser anterior à data inicial");
        }

        // Validar campos específicos para treino de força
        if (treinoRequestDTO.getSeries() != null && treinoRequestDTO.getRepeticoes() == null) {
            throw new BusinessException("Repetições são obrigatórias quando séries são informadas");
        }

        if (treinoRequestDTO.getRepeticoes() != null && treinoRequestDTO.getSeries() == null) {
            throw new BusinessException("Séries são obrigatórias quando repetições são informadas");
        }

        // Validar frequência cardíaca
        if (treinoRequestDTO.getFrequenciaCardiacaMaxima() != null &&
                treinoRequestDTO.getFrequenciaCardiacaMedia() != null &&
                treinoRequestDTO.getFrequenciaCardiacaMedia() > treinoRequestDTO.getFrequenciaCardiacaMaxima()) {
            throw new BusinessException("Frequência cardíaca média não pode ser maior que a máxima");
        }
    }
}