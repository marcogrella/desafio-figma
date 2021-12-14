package com.br.figma.repository;

import com.br.figma.model.Setor;
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
class SetorRepositoryTest {

    @Autowired
    SetorRepository setorRepository;

    @BeforeEach
    void cleanDataBase(){
        setorRepository.deleteAll();
    }



    @Test
    @DisplayName("Teste para salvar um setor")
    void salvaUmSetor_QuandoBemSucedido() {
        Setor setorASerSalvo = SetorCreator.createSetorASerSalvo("Administração");
        Setor setorSalvo = setorRepository.save(setorASerSalvo);

        Assertions.assertThat(setorSalvo).isNotNull();
        Assertions.assertThat(setorSalvo.getNome()).isNotNull();
        Assertions.assertThat(setorSalvo.getId()).isNotNull();
        Assertions.assertThat(setorSalvo.getNome()).isEqualTo(setorASerSalvo.getNome());
    }

    @Test
    @DisplayName("Teste para verificar a falha ao salvar um setor com nome de outro.")
    void deveLevantarExcecaoDataIntegrityViolationException_QuandoTentarSalvarUmSetorComNomeEmUso() {
        Setor setor1 = SetorCreator.createSetorASerSalvo( "Administração");
        Setor setor2 = SetorCreator.createSetorASerSalvo( "Administração");
        setorRepository.save(setor1);

        Assertions.assertThatThrownBy(() -> setorRepository.save( setorRepository.save(setor2)))
                .isInstanceOf(DataIntegrityViolationException.class);

    }


    @Test
    @DisplayName("Teste para buscar um setor pelo nome")
    void deveRetornarUmSetorAoConsultarPeloNome() {
        Setor setor = SetorCreator.createSetorASerSalvo("Administração");
        setorRepository.save(setor);

        Optional<Setor> setorSalvo = setorRepository.findByNome(setor.getNome());

        Assertions.assertThat(setorSalvo).isNotNull();
        Assertions.assertThat(setorSalvo.get().getNome()).isNotNull();
        Assertions.assertThat(setorSalvo.get().getId()).isNotNull();
        Assertions.assertThat(setorSalvo.get().getNome()).isEqualTo(setor.getNome());

    }


}