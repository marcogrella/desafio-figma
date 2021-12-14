package com.br.figma.utils;

import com.br.figma.model.Setor;

public class SetorCreator {

    public static Setor createSetorASerSalvo(String nome){
        return Setor.builder()
                .nome(nome)
                .build();
    }

}
