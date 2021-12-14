package com.br.figma.exceptions;


public class TrabalhadorNaoEncontradoException extends RuntimeException {
    public TrabalhadorNaoEncontradoException(String message){
        super(message);
    }
}
