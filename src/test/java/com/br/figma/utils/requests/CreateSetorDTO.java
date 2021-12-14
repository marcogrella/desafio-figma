package com.br.figma.utils.requests;

import com.br.figma.request.SetorDTO;

public class CreateSetorDTO {

    public static SetorDTO createSetorDTO(String nome){
        return SetorDTO.builder()
                .nome(nome)
                .build();
    }

}
