package com.rotina.minhaRotina.enums;

import lombok.Getter;

@Getter
public enum ModalidadeTreino {

    ACADEMIA("Academia"),
    CORRIDA("Corrida"),
    CICLISMO("Ciclismo"),
    NATACAO("Natação"),
    YOGA("Yoga"),
    FUNCIONAL("Funcional"),
    OUTROS("Outros");

    private final String descricao;
    ModalidadeTreino(String descricao) {
        this.descricao = descricao;
    }

}
