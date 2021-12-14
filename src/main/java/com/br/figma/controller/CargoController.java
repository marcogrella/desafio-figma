package com.br.figma.controller;


import com.br.figma.exceptions.CargoEmUsoException;
import com.br.figma.exceptions.CargoNaoEncontradoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.model.Cargo;

import com.br.figma.model.Setor;
import com.br.figma.request.CargoPostDTO;
import com.br.figma.request.CargoPutDTO;
import com.br.figma.service.CargoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/cargos")
public class CargoController {

    @Autowired
    private final CargoService cargoService;

    @ApiOperation(value = "Retorna uma lista de Cargos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando retorna a lista contendo todos os cargos"),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @GetMapping
    public ResponseEntity<List<Cargo>> findAll(){
        return new ResponseEntity(cargoService.findAll(), HttpStatus.OK);
    }


    @ApiOperation(value = "Atualiza um cargo na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando um cargo é atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PutMapping("/{cargoAtual}")
    public ResponseEntity<Setor> replace(@PathVariable String cargoAtual, @RequestBody @Valid CargoPutDTO cargoPutDTO) throws CargoEmUsoException, CargoNaoEncontradoException {
        return new ResponseEntity(cargoService.replace(cargoPutDTO, cargoAtual), HttpStatus.OK);
    }



    @ApiOperation(value = "Salva um cargo na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando um cargo é criado com sucesso."),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Cargo> save(@RequestBody @Valid CargoPostDTO cargoPostDTO) throws SetorNaoEncontradoException, CargoEmUsoException {
        return new ResponseEntity(cargoService.save(cargoPostDTO), HttpStatus.CREATED);
    }


}
