package com.br.figma.repository;

import com.br.figma.model.Cargo;
import com.br.figma.model.Setor;
import com.br.figma.model.Trabalhador;
import com.br.figma.utils.CargoCreator;
import com.br.figma.utils.SetorCreator;
import com.br.figma.utils.TrabalhadorCreator;
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
class TrabalhadorRepositoryTest {


    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    SetorRepository setorRepository;

    @Autowired
    TrabalhadorRepository trabalhadorRepository;

    @BeforeEach
    void cleanDataBase(){
        trabalhadorRepository.deleteAll();
        cargoRepository.deleteAll();
        setorRepository.deleteAll();

    }



    @Test
    @DisplayName("Teste para criar um Trabalhador")
    void salvaUmTrabalhador_QuandoBemSucedido() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setor);

        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo);
        Cargo cargoSalvo = cargoRepository.save(cargo);

        Trabalhador trabalhador = trabalhadorRepository.save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo));

        Assertions.assertThat(trabalhador).isNotNull();
        Assertions.assertThat(trabalhador.getNome()).isNotNull();
        Assertions.assertThat(trabalhador.getId()).isNotNull();
        Assertions.assertThat(trabalhador.getNome()).isEqualTo("João da Silva");

    }


    @Test
    @DisplayName("Teste para alterar um Trabalhador")
    void deveAlterarUmTrabalhador_QuandoBemSucedido() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setor);

        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo);
        Cargo cargo2 = CargoCreator.createCargoASerSalvo("Auxiliar Administrativo", setorSalvo);

        Cargo cargoSalvo = cargoRepository.save(cargo);
        Cargo cargoSalvo2 = cargoRepository.save(cargo2);


        Trabalhador trabalhador = trabalhadorRepository.save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo));
        Trabalhador trabalhadorSalvo = trabalhadorRepository.getById(trabalhador.getId());

        Trabalhador trabalhadorAtualizado = trabalhadorRepository
                .save(TrabalhadorCreator.updateTrabalhador(trabalhadorSalvo.getId(),
                        "Carlos Albuquerque", "952.898.250-61", cargoSalvo2));

        Assertions.assertThat(trabalhadorAtualizado.getId()).isEqualTo(trabalhador.getId());
        Assertions.assertThat(trabalhadorAtualizado.getNome()).isNotEqualTo(trabalhador.getNome());
        Assertions.assertThat(trabalhadorAtualizado.getCpf()).isNotEqualTo(trabalhador.getCpf());

    }


    @Test
    @DisplayName("Teste para verfiicar a falha ao salvar um trabalhador com cpf já em uso")
    void deveLevantarExcecao_QuandoAoTentarSalvarUmTrabalhadorComCPFJaEmUso() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setor);
        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo);
        Cargo cargoSalvo = cargoRepository.save(cargo);

        trabalhadorRepository.save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo));

        Assertions.assertThatThrownBy(
                () ->  trabalhadorRepository.save(trabalhadorRepository
                .save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo))))
                .isInstanceOf(DataIntegrityViolationException.class);

    }


    @Test
    @DisplayName("Teste para excluir um trabalhador da base de dados")
    void deveExcluirUmTrabalhador_QuandoBemSucedido() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setor);
        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo);
        Cargo cargoSalvo = cargoRepository.save(cargo);

        Trabalhador trabalhadorSalvo = trabalhadorRepository.save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo));

        Assertions.assertThatCode(() ->  trabalhadorRepository.deleteById(trabalhadorSalvo.getId())).doesNotThrowAnyException();


    }


    @Test
    @DisplayName("Teste para consultar um trabalhador da base de dados pelo CPF")
    void deveRetornarUmTrabalhador_QuandoConsultaForPorCPF() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setor);
        Cargo cargo = CargoCreator.createCargoASerSalvo("Auxiliar de Limpeza", setorSalvo);
        Cargo cargoSalvo = cargoRepository.save(cargo);

        trabalhadorRepository.save(TrabalhadorCreator.createTrabalhador("João da Silva",
                "142.793.460-62", cargoSalvo));

        Optional<Trabalhador> trabalhadorSalvo = trabalhadorRepository.findByCpf("142.793.460-62");

        Assertions.assertThat(trabalhadorSalvo.get()).isNotNull();
        Assertions.assertThat(trabalhadorSalvo.get().getId()).isNotNull();
        Assertions.assertThat(trabalhadorSalvo.get().getCpf()).isEqualTo("142.793.460-62");

    }





}