package com.br.figma.mapper;

import com.br.figma.model.Setor;
import com.br.figma.request.SetorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public abstract class SetorMapper {
    public static final SetorMapper INTANCE = Mappers.getMapper(SetorMapper.class);
    public abstract Setor toSetor(SetorDTO setorDTO);
}
