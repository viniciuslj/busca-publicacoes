package br.gov.es.pm.sbp.repository;

import br.gov.es.pm.sbp.domain.Diretorio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Diretorio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiretorioRepository extends JpaRepository<Diretorio, Long> {
    Optional<Diretorio> findOneByNomeIgnoreCase(String nome);
}
