package com.br.figma.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Trabalhador implements Serializable {

    private static final long serialVersionUID = 1959617534786663904L;

    @ApiModelProperty(value = "Código do trabalhador")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @ApiModelProperty(value = "Nome do trabalhador")
    private String nome;

    @ApiModelProperty(value = "CPF do trabalhador")
    @Column(unique=true)
    private String cpf;

    @ApiModelProperty(value = "Cargo do trabalhador")
    @OneToOne(cascade = CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "cargo_id", referencedColumnName = "id")
    private Cargo cargo;

}
