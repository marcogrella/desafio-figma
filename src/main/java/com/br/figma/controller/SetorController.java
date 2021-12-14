package com.br.figma.controller;


import com.br.figma.exceptions.SetorEmUsoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.model.Setor;
import com.br.figma.request.SetorDTO;
import com.br.figma.service.SetorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/setores")
public class SetorController {

    @Autowired
    private final SetorService setorService;

    @ApiOperation(value = "Retorna uma lista de setores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando retorna com sucesso a lista contendo todos os cargos"),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @GetMapping
    public ResponseEntity<List<Setor>> findAll(){
        return new ResponseEntity(setorService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Salva um setor na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando um setor é salvo com sucesso"),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Setor> save(@RequestBody @Valid SetorDTO setorDTO) throws SetorEmUsoException {
        return new ResponseEntity(setorService.save(setorDTO), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Atualiza um setor na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando um setor é atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PutMapping("/{setorAtual}")
    public ResponseEntity<Setor> replace(@PathVariable String setorAtual, @RequestBody @Valid SetorDTO setorDTO) throws SetorEmUsoException, SetorNaoEncontradoException {
        return new ResponseEntity(setorService.replace(setorDTO, setorAtual), HttpStatus.OK);
    }

}
