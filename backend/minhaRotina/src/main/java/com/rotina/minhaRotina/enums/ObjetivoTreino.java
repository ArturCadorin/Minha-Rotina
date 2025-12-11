package com.rotina.minhaRotina.enums;

import lombok.Getter;

@Getter
public enum ObjetivoTreino {

    /*
        treinos academia/funcional
     */
    HIPERTROFIA("Hipertrofia"),
    GANHO_MASSA("Ganho de massa"),
    MANUTENCAO("Manutenção"),

    /*
        treinos em geral
     */
    EMAGRECIMENTO("Emagrecimento"),
    OUTROS("Outros"),
    FORCA("Força"),
    RESISTENCIA("Resistência");

    private final String descricao;
    ObjetivoTreino(String descricao) {
        this.descricao = descricao;
    }
}
