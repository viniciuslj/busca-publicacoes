package br.gov.es.pm.sbp.repository;

import br.gov.es.pm.sbp.domain.LogBusca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LogBusca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogBuscaRepository extends JpaRepository<LogBusca, Long> {

    @Query("select logBusca from LogBusca logBusca where logBusca.user.login = ?#{principal.username}")
    List<LogBusca> findByUserIsCurrentUser();

}
