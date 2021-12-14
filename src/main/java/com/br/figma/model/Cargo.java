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
public class Cargo implements Serializable {

    @ApiModelProperty(value = "Código do Cargo")
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Nome do Cargo")
    @Column(unique=true)
    private String nome;

    @ApiModelProperty(value = "Setor do Cargo")
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "setor_id", referencedColumnName = "id")
    private Setor setor;


}
