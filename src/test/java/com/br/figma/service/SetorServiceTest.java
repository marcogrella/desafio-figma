package com.br.figma.service;

import com.br.figma.exceptions.SetorEmUsoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.mapper.SetorMapper;
import com.br.figma.model.Setor;
import com.br.figma.repository.SetorRepository;
import com.br.figma.utils.SetorCreator;
import com.br.figma.utils.requests.CreateSetorDTO;
import net.bytebuddy.pool.TypePool;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class SetorServiceTest {


    @Mock
    private SetorRepository setorRepositoryMock;

    @InjectMocks
    private SetorService serviceTest;

    @Test
    @DisplayName("Teste para salvar um Setor")
    void save_PersiteUmSetor_QuandoBemSucedido() throws SetorEmUsoException {

        BDDMockito.when(setorRepositoryMock.save(ArgumentMatchers.any(Setor.class)))
                .thenReturn(SetorCreator.createSetorValido());

        Setor setor = serviceTest.save(CreateSetorDTO.createSetorDTO("Administração"));

        Assertions.assertThat(setor).isNotNull();
        Assertions.assertThat(setor)
                .isNotNull()
                .isEqualTo(SetorCreator.createSetorValido());

    }

    @Test
    @DisplayName("Teste para atualizar um Setor")
    void replace_AlteraUmSetorEPersiste_QuandoBemSucedido() throws SetorEmUsoException, SetorNaoEncontradoException {

        BDDMockito.when(setorRepositoryMock.findByNome(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(SetorCreator.createSetorValido()), Optional.empty());

        BDDMockito.when(setorRepositoryMock.save(ArgumentMatchers.any(Setor.class)))
                .thenReturn(SetorCreator.createSetorParaAtualizacao());

        Setor setor = serviceTest.replace(CreateSetorDTO.createSetorDTO("Recursos Humanos"), "Administração");


        Assertions.assertThat(setor).isNotNull();
        Assertions.assertThat(setor.getNome()).isEqualTo("Recursos Humanos");

    }

    @Test
    @DisplayName("Teste para verificar se exceção SetorNaoEncontradoException quando um setor não for encontrado.")
    void findOrThrowException_deveLevantarExcecaoQuandoSetorNaoForEncontrado() {

        BDDMockito.when(setorRepositoryMock.findByNome(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.empty());

        String setor = "Financeiro";

        Assertions.assertThatThrownBy(() -> serviceTest.findOrThrowException(setor))
                .isInstanceOf(SetorNaoEncontradoException.class)
                .hasMessage("O setor com o nome " + setor + " não foi encontrado");


    }

    @Test
    @DisplayName("Teste para verificar se exceção SetorEmUsoException quando um setor já estiver cadastrado.")
    void verificarSetorEmUso_deveLevantarExcecaoQuandoSetorJaEstiverCadastrado() {

        BDDMockito.when(setorRepositoryMock.findByNome(ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(SetorCreator.createSetorParaAtualizacao()));

        String setor = "Recursos Humanos";

        Assertions.assertThatThrownBy(() -> serviceTest.verificarSetorEmUso(setor))
                .isInstanceOf(SetorEmUsoException.class)
                .hasMessage("O setor com o nome " + setor + " já está em uso");


    }

    @Test
    @DisplayName("Teste para verificar se exceção SetorEmUsoException quando um setor já estiver cadastrado.")
    void findAll_deveTrazerUmaListaDeSetoresCadastrados() {

        BDDMockito.when(setorRepositoryMock.findAll())
                .thenReturn(List.of(SetorCreator.createSetorValido()));


        List<Setor> lista =  serviceTest.findAll();

        Assertions.assertThat(lista).isNotNull();
        Assertions.assertThat(lista)
                .isNotEmpty()
                .hasSize(1);


    }

}