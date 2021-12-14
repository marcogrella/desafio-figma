package com.br.figma.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class TrabalhadorPutDTO {

    @ApiModelProperty(value = "Código do trabalhador cadastrado no Banco de Dados. Ex: 1")
    @NotNull(message = "O campo 'id' não pode ser nulo.")
    private Long id;

    @ApiModelProperty(value = "Novo nome do trabalhador para ser atualizado.")
    @NotEmpty(message = "O campo NOME não pode estar em branco ou nulo.")
    @Size(min = 2, max = 100, message = "O campo NOME deve possuir entre 2 e 100 caractéres.")
    private String nome;

    @ApiModelProperty(value = "CPF do trabalhador. Ex: 140.051.110-09 ou manter o mesmo.")
    @CPF(message="CPF inválido.")
    @NotEmpty(message = "O campo CPF é obrigatório.")
    @Length(min = 1, max = 18, message = "O campo CPF deve possuir entre 1 e 10 caractéres.")
    private String cpf;

    @ApiModelProperty(value = "Novo cargo do trabalhador. Ex: 'auxiliar administrativo'.")
    @NotEmpty(message = "O campo 'CARGO' não pode estar em branco ou nulo.")
    @Size(min = 2, max = 100, message = "O campo 'CARGO' deve possuir entre 2 e 100 caractéres.")
    private String cargo;

}
