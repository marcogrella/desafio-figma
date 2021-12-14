package com.br.figma.utils;

import com.br.figma.model.Cargo;
import com.br.figma.model.Setor;
import com.br.figma.model.Trabalhador;

public class TrabalhadorCreator {

    public static Trabalhador createTrabalhador(String nome, String cpf, Cargo cargo){
        return Trabalhador.builder()
                .nome(nome)
                .cpf(cpf)
                .cargo(cargo)
                .build();
    }

    public static Trabalhador updateTrabalhador(Long id, String nome, String cpf, Cargo cargo){
        return Trabalhador.builder()
                .id(id)
                .nome(nome)
                .cpf(cpf)
                .cargo(cargo)
                .build();
    }
}
