package com.br.figma.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Setor implements Serializable {

    private static final long serialVersionUID = 4889388367078304231L;

    @ApiModelProperty(value = "Código do Setor")
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Nome do Setor")
    @Column(unique=true)
    private String nome;



}
