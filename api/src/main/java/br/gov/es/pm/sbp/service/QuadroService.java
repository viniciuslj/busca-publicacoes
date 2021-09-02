package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.domain.Quadro;
import br.gov.es.pm.sbp.repository.QuadroRepository;
import br.gov.es.pm.sbp.repository.search.QuadroSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Quadro}.
 */
@Service
@Transactional
public class QuadroService {

    private final Logger log = LoggerFactory.getLogger(QuadroService.class);

    private final QuadroRepository quadroRepository;

    private final QuadroSearchRepository quadroSearchRepository;

    public QuadroService(QuadroRepository quadroRepository, QuadroSearchRepository quadroSearchRepository) {
        this.quadroRepository = quadroRepository;
        this.quadroSearchRepository = quadroSearchRepository;
    }

    /**
     * Save a quadro.
     *
     * @param quadro the entity to save.
     * @return the persisted entity.
     */
    public Quadro save(Quadro quadro) {
        log.debug("Request to save Quadro : {}", quadro);
        Quadro result = quadroRepository.save(quadro);
        quadroSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the quadros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Quadro> findAll(Pageable pageable) {
        log.debug("Request to get all Quadros");
        return quadroRepository.findAll(pageable);
    }


    /**
     * Get one quadro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Quadro> findOne(Long id) {
        log.debug("Request to get Quadro : {}", id);
        return quadroRepository.findById(id);
    }

    /**
     * Delete the quadro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Quadro : {}", id);
        quadroRepository.deleteById(id);
        quadroSearchRepository.deleteById(id);
    }

    /**
     * Search for the quadro corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Quadro> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Quadros for query {}", query);
        return quadroSearchRepository.search(queryStringQuery(query), pageable);    }
}
