package br.gov.es.pm.sbp.web.rest;

import br.gov.es.pm.sbp.domain.Quadro;
import br.gov.es.pm.sbp.service.QuadroService;
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
 * REST controller for managing {@link br.gov.es.pm.sbp.domain.Quadro}.
 */
@RestController
@RequestMapping("/api")
public class QuadroResource {

    private final Logger log = LoggerFactory.getLogger(QuadroResource.class);

    private static final String ENTITY_NAME = "quadro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuadroService quadroService;

    public QuadroResource(QuadroService quadroService) {
        this.quadroService = quadroService;
    }

    /**
     * {@code GET  /quadros} : get all the quadros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quadros in body.
     */
    @GetMapping("/quadros")
    public ResponseEntity<List<Quadro>> getAllQuadros(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Quadros");
        Page<Quadro> page = quadroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quadros/:id} : get the "id" quadro.
     *
     * @param id the id of the quadro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quadro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quadros/{id}")
    public ResponseEntity<Quadro> getQuadro(@PathVariable Long id) {
        log.debug("REST request to get Quadro : {}", id);
        Optional<Quadro> quadro = quadroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quadro);
    }

    /**
     * {@code SEARCH  /_search/quadros?query=:query} : search for the quadro corresponding
     * to the query.
     *
     * @param query the query of the quadro search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/quadros")
    public ResponseEntity<List<Quadro>> searchQuadros(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Quadros for query {}", query);
        Page<Quadro> page = quadroService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
