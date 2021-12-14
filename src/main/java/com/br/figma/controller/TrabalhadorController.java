package com.br.figma.controller;


import com.br.figma.exceptions.CargoEmUsoException;
import com.br.figma.exceptions.CargoNaoEncontradoException;
import com.br.figma.exceptions.CpfEmUsoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.model.Cargo;
import com.br.figma.model.Trabalhador;
import com.br.figma.request.CargoPostDTO;
import com.br.figma.request.TrabalhadorPostDTO;
import com.br.figma.request.TrabalhadorPutDTO;
import com.br.figma.service.SetorService;
import com.br.figma.service.TrabalhadorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/trabalhadores")
public class TrabalhadorController {

    @Autowired
    private final TrabalhadorService trabalhadorService;

    @ApiOperation(value = "Salva um trabalhador na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quando um trabalhador é salvo com sucesso"),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Trabalhador> save(@RequestBody @Valid TrabalhadorPostDTO trabalhadorPostDTO)
            throws CargoNaoEncontradoException, CpfEmUsoException {
        return new ResponseEntity(trabalhadorService.save(trabalhadorPostDTO), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Retorna uma lista de trabalhadores utilizando paginação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando retorna uma lista contendo os trabalhadores"),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @GetMapping
    public ResponseEntity<Page<Trabalhador>> list(Pageable peageable){
        return ResponseEntity.ok(trabalhadorService.listAll(peageable));


    }

    @ApiOperation(value = "Exclui um trabalhador da base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Quando um trabalhador é excluído com sucesso"),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        trabalhadorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Atualiza um trabalhador na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quando um trabalhador é atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Quando ocorre uma falha na operação."),
            @ApiResponse(code = 500, message = "Quando ocorre um erro interno do servidor"),
    })
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid TrabalhadorPutDTO trabalhadorPutDTO) throws CpfEmUsoException,
            CargoNaoEncontradoException {
        trabalhadorService.replace(trabalhadorPutDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
