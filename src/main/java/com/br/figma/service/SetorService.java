package com.br.figma.service;

import com.br.figma.exceptions.SetorEmUsoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.mapper.SetorMapper;
import com.br.figma.model.Setor;
import com.br.figma.repository.SetorRepository;
import com.br.figma.request.SetorDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SetorService {

    @Autowired
    private final SetorRepository setorRepository;

    @Transactional(rollbackFor = Exception.class)
    public Setor save(SetorDTO setorDTO) throws SetorEmUsoException {
       Setor setor = SetorMapper.INTANCE.toSetor(setorDTO);
       setor.setNome(setor.getNome().toUpperCase());
       verificarSetorEmUso(setor.getNome());
       return setorRepository.save(setor);
    }

    public Setor replace(SetorDTO setorDTO, String setorAtual) throws SetorNaoEncontradoException, SetorEmUsoException {
        Setor setorDB = findOrThrowException(setorAtual);
        verificarSetorEmUso(setorDTO.getNome());
        Setor setor = SetorMapper.INTANCE.toSetor(setorDTO);
        setorDB.setNome(setor.getNome().toUpperCase());
        return setorRepository.save(setorDB);
    }


    public List<Setor> findAll(){
        return setorRepository.findAll();
    }


    public Setor findOrThrowException(String setorAtual) throws SetorNaoEncontradoException {
        return setorRepository.findByNome(setorAtual.toUpperCase())
                .orElseThrow(() -> new SetorNaoEncontradoException("O setor com o nome " + setorAtual + " não foi encontrado"));
    }


    public void verificarSetorEmUso(String setor) throws SetorEmUsoException{
        Optional<Setor> setorVerificado = setorRepository.findByNome(setor.toUpperCase());
        if (setorVerificado.isPresent()){
            throw new SetorEmUsoException("O setor com o nome " + setor + " já está em uso");
        }
    }




}
