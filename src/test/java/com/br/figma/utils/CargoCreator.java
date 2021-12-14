package com.br.figma.utils;

import com.br.figma.model.Cargo;
import com.br.figma.model.Setor;

public class CargoCreator {

    public static Cargo createCargoASerSalvo(String nome, Setor setor){
        return Cargo.builder()
                .nome(nome)
                .setor(setor)
                .build();
    }

}
