package com.br.figma.service;

import com.br.figma.exceptions.CargoNaoEncontradoException;
import com.br.figma.exceptions.CpfEmUsoException;
import com.br.figma.exceptions.TrabalhadorNaoEncontradoException;
import com.br.figma.model.Cargo;
import com.br.figma.model.Trabalhador;
import com.br.figma.repository.TrabalhadorRepository;
import com.br.figma.request.TrabalhadorPostDTO;
import com.br.figma.request.TrabalhadorPutDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TrabalhadorService {

    @Autowired
    private final TrabalhadorRepository trabalhadorRepository;

    @Autowired
    private final CargoService cargoService;

    public Page<Trabalhador> listAll(Pageable peageable){
        return trabalhadorRepository.findAll(peageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public Trabalhador save(TrabalhadorPostDTO trabalhadorPDTO) throws CargoNaoEncontradoException, CpfEmUsoException {
        Cargo cargo = cargoService.findOrThrowException(trabalhadorPDTO.getCargo());
        verificarCpfEmUso(trabalhadorPDTO.getCpf());
        Trabalhador trabalhador = Trabalhador.builder()
                .nome(trabalhadorPDTO.getNome())
                .cpf(trabalhadorPDTO.getCpf())
                .cargo(cargo)
                .build();
        return trabalhadorRepository.save(trabalhador);
    }

    public void delete(Long id){
        Trabalhador trabalhador = findByIdOrThrowException(id);
        trabalhadorRepository.deleteById(trabalhador.getId());

    }


    public void replace(TrabalhadorPutDTO trabalhadorPutDTO) throws CargoNaoEncontradoException, CpfEmUsoException {
        Trabalhador trabalhadorDB = findByIdOrThrowException(trabalhadorPutDTO.getId());
        Cargo cargo = cargoService.findOrThrowException(trabalhadorPutDTO.getCargo());

        if (trabalhadorPutDTO.getCpf().equals(trabalhadorDB.getCpf())){
            trabalhadorDB.setCpf(trabalhadorPutDTO.getCpf());
        }else{
            verificarCpfEmUso(trabalhadorPutDTO.getCpf());
            trabalhadorDB.setCpf(trabalhadorPutDTO.getCpf());
        }

        trabalhadorDB.setCargo(cargo);
        trabalhadorDB.setNome(trabalhadorPutDTO.getNome());
        trabalhadorRepository.save(trabalhadorDB);
    }


    private void verificarCpfEmUso(String cpf) throws CpfEmUsoException {
        Optional<Trabalhador> cpfVerificado = trabalhadorRepository.findByCpf(cpf);
        if (cpfVerificado.isPresent()){
            throw new CpfEmUsoException("O cpf com o número " + cpf + " já está em uso");
        }
    }


    public Trabalhador findByIdOrThrowException(Long id){
        return trabalhadorRepository.findById(id).
                        orElseThrow(() -> new TrabalhadorNaoEncontradoException("Trabalhador com o id " + id +
                                " não foi encontrado"));
    }


}
