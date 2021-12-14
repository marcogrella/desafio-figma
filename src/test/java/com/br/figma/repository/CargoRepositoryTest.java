package com.br.figma.repository;


import com.br.figma.model.Cargo;
import com.br.figma.model.Setor;
import com.br.figma.utils.CargoCreator;
import com.br.figma.utils.SetorCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CargoRepositoryTest {

    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    SetorRepository setorRepository;

    @BeforeEach
    void cleanDataBase(){
        cargoRepository.deleteAll();
        setorRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste para salvar um cargo")
    void salvaUmCargo_QuandoBemSucedido() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        setorRepository.save(setor);

        Optional<Setor> setorSalvo = setorRepository.findByNome(setor.getNome());

        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo.get());
        Cargo cargoSalvo = cargoRepository.save(cargo);

        Assertions.assertThat(cargoSalvo).isNotNull();
        Assertions.assertThat(cargoSalvo.getNome()).isNotNull();
        Assertions.assertThat(cargoSalvo.getId()).isNotNull();
        Assertions.assertThat(cargoSalvo.getSetor().getNome()).isEqualTo(setor.getNome());
    }


    @Test
    @DisplayName("Teste para verificar falha ao salvar um cargo pertencente a outro setor")
    void deveLevantarExcecao_QuandoHouverTentativaDeSalvarUmCargoPertencenteAOutroSetor() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        setorRepository.save(setor);

        Optional<Setor> setorSalvo = setorRepository.findByNome(setor.getNome());

        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo.get());
        cargoRepository.save(cargo);
        Cargo cargoConflitante = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo.get());


        Assertions.assertThatThrownBy(() -> cargoRepository.save(cargoConflitante))
                .isInstanceOf(DataIntegrityViolationException.class);
    }




}