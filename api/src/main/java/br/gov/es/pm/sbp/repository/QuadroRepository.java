package br.gov.es.pm.sbp.repository;

import br.gov.es.pm.sbp.domain.Quadro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quadro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuadroRepository extends JpaRepository<Quadro, Long> {

}
