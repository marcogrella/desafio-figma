package com.br.figma.mapper;

import com.br.figma.model.Cargo;
import com.br.figma.request.CargoPostDTO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CargoMapper {
    public static final CargoMapper INTANCE = Mappers.getMapper(CargoMapper.class);
    public abstract Cargo toCargo(CargoPostDTO cargoPostDTO);

}
