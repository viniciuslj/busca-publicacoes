package br.gov.es.pm.sbp.web.rest;

import br.gov.es.pm.sbp.domain.Pagina;
import br.gov.es.pm.sbp.service.PaginaService;
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
 * REST controller for managing {@link br.gov.es.pm.sbp.domain.Pagina}.
 */
@RestController
@RequestMapping("/api")
public class PaginaResource {

    private final Logger log = LoggerFactory.getLogger(PaginaResource.class);

    private static final String ENTITY_NAME = "pagina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaginaService paginaService;

    public PaginaResource(PaginaService paginaService) {
        this.paginaService = paginaService;
    }

    /**
     * {@code GET  /paginas} : get all the paginas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paginas in body.
     */
    @GetMapping("/paginas")
    public ResponseEntity<List<Pagina>> getAllPaginas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Paginas");
        Page<Pagina> page = paginaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paginas/:id} : get the "id" pagina.
     *
     * @param id the id of the pagina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pagina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paginas/{id}")
    public ResponseEntity<Pagina> getPagina(@PathVariable Long id) {
        log.debug("REST request to get Pagina : {}", id);
        Optional<Pagina> pagina = paginaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagina);
    }

    /**
     * {@code SEARCH  /_search/paginas?query=:query} : search for the pagina corresponding
     * to the query.
     *
     * @param query the query of the pagina search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/paginas")
    public ResponseEntity<List<Pagina>> searchPaginas(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Paginas for query {}", query);
        Page<Pagina> page = paginaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
