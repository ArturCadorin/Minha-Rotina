package com.rotina.minhaRotina.mapper;

import com.rotina.minhaRotina.dto.request.EstudoRequestDTO;
import com.rotina.minhaRotina.dto.response.EstudoResponseDTO;
import com.rotina.minhaRotina.entity.Estudo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EstudoMapper {

    // ✅ RequestDTO → Entity (SEM @Mapping - usar constructor/setters)
    Estudo toEntity(EstudoRequestDTO estudoRequestDTO);

    // ✅ Entity → ResponseDTO
    @Mapping(target = "tipoTarefa", constant = "ESTUDO")
    EstudoResponseDTO toDTO(Estudo estudo);

    // ✅ Update entity from DTO (SEM @Mapping - usar setters)
    void updateEntityFromDTO(EstudoRequestDTO estudoRequestDTO, @MappingTarget Estudo estudo);

    // ❌ REMOVIDO: usuarioIdToUsuario duplicado - usar do TarefaMapper
}