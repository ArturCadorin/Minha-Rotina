package com.rotina.minhaRotina.enums;

import lombok.Getter;

@Getter
public enum BiotipoFisico {

    ECTOMORFO("Ectomorfo"),
    MESOMORFO("Mesomorfo"),
    ENDOMORFO("Endomorfo");

    private final String descricao;
    BiotipoFisico(String descricao) {
        this.descricao = descricao;
    }
}
