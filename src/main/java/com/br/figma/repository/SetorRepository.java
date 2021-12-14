package com.br.figma.repository;


import com.br.figma.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    Optional<Setor> findByNome(String nome);

}
