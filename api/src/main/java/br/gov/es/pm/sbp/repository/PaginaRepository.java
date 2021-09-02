package br.gov.es.pm.sbp.repository;

import br.gov.es.pm.sbp.domain.Pagina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pagina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaginaRepository extends JpaRepository<Pagina, Long> {
    void deleteByDocumentoId(Long id);
}
