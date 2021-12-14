package com.br.figma.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class TrabalhadorPostDTO {

    @ApiModelProperty(value = "Nome do trabalhador. Ex: João da Silva")
    @NotEmpty(message = "O campo NOME não pode estar em branco ou nulo.")
    @Size(min = 2, max = 100, message = "O campo NOME deve possuir entre 2 e 100 caractéres.")
    private String nome;

    @ApiModelProperty(value = "CPF do trabalhador. Ex: 140.051.110-09")
    @CPF(message="CPF inválido.")
    @NotEmpty(message = "O campo CPF é obrigatório.")
    @Length(min = 1, max = 18, message = "O campo CPF deve possuir entre 1 e 10 caractéres.")
    private String cpf;

    @ApiModelProperty(value = "Cargo do trabalhador. Ex: 'Vendedor'")
    @NotEmpty(message = "O campo 'CARGO' não pode estar em branco ou nulo.")
    @Size(min = 2, max = 100, message = "O campo 'CARGO' deve possuir entre 2 e 100 caractéres.")
    private String cargo;



}
