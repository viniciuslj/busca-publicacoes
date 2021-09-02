package br.gov.es.pm.sbp.web.rest;

import br.gov.es.pm.sbp.domain.LogBusca;
import br.gov.es.pm.sbp.security.AuthoritiesConstants;
import br.gov.es.pm.sbp.service.LogBuscaService;
import br.gov.es.pm.sbp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.gov.es.pm.sbp.domain.LogBusca}.
 */
@RestController
@RequestMapping("/api")
public class LogBuscaResource {

    private final Logger log = LoggerFactory.getLogger(LogBuscaResource.class);

    private static final String ENTITY_NAME = "logBusca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogBuscaService logBuscaService;

    public LogBuscaResource(LogBuscaService logBuscaService) {
        this.logBuscaService = logBuscaService;
    }

    /**
     * {@code GET  /log-buscas} : get all the logBuscas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logBuscas in body.
     */
    @GetMapping("/log-buscas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<LogBusca>> getAllLogBuscas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of LogBuscas");
        Page<LogBusca> page = logBuscaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-buscas/:id} : get the "id" logBusca.
     *
     * @param id the id of the logBusca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logBusca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-buscas/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<LogBusca> getLogBusca(@PathVariable Long id) {
        log.debug("REST request to get LogBusca : {}", id);
        Optional<LogBusca> logBusca = logBuscaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logBusca);
    }

    /**
     * {@code SEARCH  /_search/log-buscas?query=:query} : search for the logBusca corresponding
     * to the query.
     *
     * @param query the query of the logBusca search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/log-buscas")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<LogBusca>> searchLogBuscas(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of LogBuscas for query {}", query);
        Page<LogBusca> page = logBuscaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
