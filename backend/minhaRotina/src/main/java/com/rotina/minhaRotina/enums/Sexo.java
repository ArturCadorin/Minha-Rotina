package com.rotina.minhaRotina.enums;

import lombok.Getter;

@Getter
public enum Sexo {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTROS("Outros");

    private final String descricao;
    Sexo(String descricao) {
        this.descricao = descricao;
    }
}
