package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.domain.LogBusca;
import br.gov.es.pm.sbp.domain.User;
import br.gov.es.pm.sbp.repository.LogBuscaRepository;
import br.gov.es.pm.sbp.repository.search.LogBuscaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LogBusca}.
 */
@Service
@Transactional
public class LogBuscaService {

    private final Logger log = LoggerFactory.getLogger(LogBuscaService.class);

    private final LogBuscaRepository logBuscaRepository;

    private final LogBuscaSearchRepository logBuscaSearchRepository;

    private final UserService userService;

    public LogBuscaService(
        LogBuscaRepository logBuscaRepository,
        LogBuscaSearchRepository logBuscaSearchRepository,
        UserService userService) {
        this.logBuscaRepository = logBuscaRepository;
        this.logBuscaSearchRepository = logBuscaSearchRepository;
        this.userService = userService;
    }

    /**
     * Save a logBusca.
     *
     * @param logBusca the entity to save.
     * @return the persisted entity.
     */
    public LogBusca save(LogBusca logBusca) {
        log.debug("Request to save LogBusca : {}", logBusca);
        User user = userService.getUserByLogin().get();
        logBusca.setUser(user);
        LogBusca result = logBuscaRepository.save(logBusca);
        logBuscaSearchRepository.save(result);

        return result;
    }

    /**
     * Get all the logBuscas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogBusca> findAll(Pageable pageable) {
        log.debug("Request to get all LogBuscas");
        return logBuscaRepository.findAll(pageable);
    }


    /**
     * Get one logBusca by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LogBusca> findOne(Long id) {
        log.debug("Request to get LogBusca : {}", id);
        return logBuscaRepository.findById(id);
    }

    /**
     * Search for the logBusca corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LogBusca> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LogBuscas for query {}", query);
        return logBuscaSearchRepository.search(queryStringQuery(query), pageable);    }
}
