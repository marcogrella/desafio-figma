package com.br.figma.utils;

import com.br.figma.model.Setor;

public class SetorCreator {

    public static Setor createSetorASerSalvo(String nome){
        return Setor.builder()
                .nome(nome)
                .build();
    }

    public static Setor createSetorValido(){
        return Setor.builder()
                .id(1L)
                .nome("Administração")
                .build();
    }

    public static Setor createSetorParaAtualizacao(){
        return Setor.builder()
                .nome("Recursos Humanos")
                .build();
    }

}
