package com.rotina.minhaRotina.enums;

import lombok.Getter;

@Getter
public enum IntensidadeTreino {

    /*
        divisão por níveis de intensidade
     */
    MUITO_LEVE("Muito leve"),
    LEVE("Leve"),
    MODERADA("Moderada"),
    ALTA("Alta"),
    MUITO_ALTA("Muito alta"),

    /*
        divisão por zonas cardiacas
     */
    ZONA_1("Zona 1"),
    ZONA_2("Zona 2"),
    ZONA_3("Zona 3"),
    ZONA_4("Zona 4"),
    ZONA_5("Zona 5"),
    ZONA_6("Zona 6"),
    ZONA_7("Zona 7");

    private final String descricao;
    IntensidadeTreino(String descricao) {
        this.descricao = descricao;
    }
}
