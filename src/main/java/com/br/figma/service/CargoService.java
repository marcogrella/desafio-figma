package com.br.figma.service;

import com.br.figma.exceptions.CargoEmUsoException;
import com.br.figma.exceptions.CargoNaoEncontradoException;
import com.br.figma.exceptions.SetorNaoEncontradoException;
import com.br.figma.mapper.CargoMapper;
import com.br.figma.model.Cargo;
import com.br.figma.model.Setor;
import com.br.figma.repository.CargoRepository;
import com.br.figma.request.CargoPostDTO;
import com.br.figma.request.CargoPutDTO;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Builder
@Service
public class CargoService {

    @Autowired
    private final SetorService setorService;

    private final CargoRepository cargoRepository;


    public List<Cargo> findAll(){
        return cargoRepository.findAll();
    }


    @Transactional(rollbackFor = Exception.class)
    public Cargo save(CargoPostDTO cargoPostDTO) throws SetorNaoEncontradoException, CargoEmUsoException {
        Setor setor = setorService.findOrThrowException(cargoPostDTO.getSetor().getNome().toUpperCase());
        Cargo cargoMapped = CargoMapper.INTANCE.toCargo(cargoPostDTO);
        verificarCargoEmUso(cargoMapped.getNome());
        cargoMapped.setNome(cargoMapped.getNome().toUpperCase());
        cargoMapped.setSetor(setor);
        return cargoRepository.save(cargoMapped);
    }

    public Cargo replace(CargoPutDTO cargoPutDTO, String cargoAtual) throws CargoNaoEncontradoException, CargoEmUsoException {
        Cargo cargoDB = findOrThrowException(cargoAtual);
        verificarCargoEmUso(cargoPutDTO.getNome());
        cargoDB.setNome(cargoPutDTO.getNome().toUpperCase());
        return cargoRepository.save(cargoDB);
    }

    public Cargo findOrThrowException(String nomeCargo) throws CargoNaoEncontradoException {
        return cargoRepository.findByNome(nomeCargo.toUpperCase())
                .orElseThrow(() -> new CargoNaoEncontradoException("O cargo com o nome " + nomeCargo + " não foi encontrado"));
    }

    private void verificarCargoEmUso(String nomeCargo) throws CargoEmUsoException {
        Optional<Cargo> cargoVerificado = cargoRepository.findByNome(nomeCargo.toUpperCase());
        if (cargoVerificado.isPresent()){
            throw new CargoEmUsoException("O cargo com o nome " + nomeCargo + " já está em uso");
        }
    }


}
