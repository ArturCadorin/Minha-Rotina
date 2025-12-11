package com.rotina.minhaRotina.mapper;

import com.rotina.minhaRotina.dto.request.UsuarioRequestDTO;
import com.rotina.minhaRotina.dto.response.UsuarioResponseDTO;
import com.rotina.minhaRotina.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // ✅ RequestDTO → Entity (SEM @Mapping - usar constructor/setters)
    Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

    // ✅ Entity → ResponseDTO
    UsuarioResponseDTO toDTO(Usuario usuario);

    // ✅ Update entity from DTO (SEM @Mapping - usar setters)
    void updateEntityFromDTO(UsuarioRequestDTO usuarioRequestDTO, @MappingTarget Usuario usuario);
}