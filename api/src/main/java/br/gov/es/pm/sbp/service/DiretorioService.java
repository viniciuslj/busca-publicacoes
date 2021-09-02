package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.domain.Diretorio;
import br.gov.es.pm.sbp.repository.DiretorioRepository;
import br.gov.es.pm.sbp.repository.search.DiretorioSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Diretorio}.
 */
@Service
@Transactional
public class DiretorioService {

    private final Logger log = LoggerFactory.getLogger(DiretorioService.class);

    private final DiretorioRepository diretorioRepository;

    private final DiretorioSearchRepository diretorioSearchRepository;

    public DiretorioService(DiretorioRepository diretorioRepository, DiretorioSearchRepository diretorioSearchRepository) {
        this.diretorioRepository = diretorioRepository;
        this.diretorioSearchRepository = diretorioSearchRepository;
    }

    /**
     * Save a diretorio.
     *
     * @param diretorio the entity to save.
     * @return the persisted entity.
     */
    public Diretorio save(Diretorio diretorio) {
        log.debug("Request to save Diretorio : {}", diretorio);
        Diretorio result = diretorioRepository.save(diretorio);
        diretorioSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the diretorios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Diretorio> findAll(Pageable pageable) {
        log.debug("Request to get all Diretorios");
        return diretorioRepository.findAll(pageable);
    }


    /**
     * Get one diretorio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Diretorio> findOne(Long id) {
        log.debug("Request to get Diretorio : {}", id);
        return diretorioRepository.findById(id);
    }

    /**
     * Delete the diretorio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Diretorio : {}", id);
        diretorioRepository.deleteById(id);
        diretorioSearchRepository.deleteById(id);
    }

    /**
     * Search for the diretorio corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Diretorio> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Diretorios for query {}", query);
        return diretorioSearchRepository.search(queryStringQuery(query), pageable);    }
}
