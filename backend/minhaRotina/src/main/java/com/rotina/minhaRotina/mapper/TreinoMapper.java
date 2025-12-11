package com.rotina.minhaRotina.mapper;

import com.rotina.minhaRotina.dto.request.TreinoRequestDTO;
import com.rotina.minhaRotina.dto.response.TreinoResponseDTO;
import com.rotina.minhaRotina.entity.Treino;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TreinoMapper {

    // ✅ RequestDTO → Entity (SEM @Mapping - usar constructor/setters)
    Treino toEntity(TreinoRequestDTO treinoRequestDTO);

    // ✅ Entity → ResponseDTO
    @Mapping(target = "tipoTarefa", constant = "TREINO")
    @Mapping(target = "cargaTotal", expression = "java(calcularCargaTotal(treino))")
    TreinoResponseDTO toDTO(Treino treino);

    // ✅ Update entity from DTO (SEM @Mapping - usar setters)
    void updateEntityFromDTO(TreinoRequestDTO treinoRequestDTO, @MappingTarget Treino treino);

    // ✅ Método para calcular carga total automaticamente
    default Double calcularCargaTotal(Treino treino) {
        if (treino.getSeries() != null && treino.getRepeticoes() != null && treino.getCargaRepeticao() != null) {
            return treino.getSeries() * treino.getRepeticoes() * treino.getCargaRepeticao();
        }
        return treino.getCargaTotal();
    }

    // ❌ REMOVIDO: usuarioIdToUsuario duplicado - usar do TarefaMapper
}