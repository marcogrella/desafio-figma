package com.br.figma.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class SetorDTO implements Serializable {

    private static final long serialVersionUID = 764795433641528668L;

    @ApiModelProperty(value = "Nome do setor. Ex: 'Engenharia'.")
    @NotEmpty(message = "O campo NOME não pode estar em branco ou nulo.")
    @Size(min = 2, max = 100, message = "O campo NOME deve possuir entre 2 e 100 caractéres.")
    private String nome;


}
