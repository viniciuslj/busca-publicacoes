package br.gov.es.pm.sbp.repository;

import br.gov.es.pm.sbp.domain.Documento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findAllByOrderByNomeDesc();
}
