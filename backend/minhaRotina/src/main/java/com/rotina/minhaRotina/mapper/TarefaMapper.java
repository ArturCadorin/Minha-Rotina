package com.rotina.minhaRotina.mapper;

import com.rotina.minhaRotina.dto.response.TarefaResponseDTO;
import com.rotina.minhaRotina.entity.Tarefa;
import com.rotina.minhaRotina.entity.Usuario;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    // ❌ REMOVIDO: toEntity para Tarefa abstrata - não funciona
    // ❌ REMOVIDO: updateEntityFromDTO para Tarefa abstrata

    // ✅ APENAS Entity → ResponseDTO
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "tipoTarefa", expression = "java(getTipoTarefa(tarefa))")
    TarefaResponseDTO toDTO(Tarefa tarefa);

    // ✅ Metodo para determinar o tipo da tarefa
    default String getTipoTarefa(Tarefa tarefa) {
        if (tarefa instanceof com.rotina.minhaRotina.entity.Estudo) {
            return "ESTUDO";
        } else if (tarefa instanceof com.rotina.minhaRotina.entity.Treino) {
            return "TREINO";
        }
        return "TAREFA";
    }

    // ✅ Metodo para converter ID do usuário em entidade Usuario (COMPARTILHADO)
    @Named("usuarioIdToUsuario")
    default Usuario usuarioIdToUsuario(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return usuario;
    }
}