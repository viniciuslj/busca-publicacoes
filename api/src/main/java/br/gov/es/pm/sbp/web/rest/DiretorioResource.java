package br.gov.es.pm.sbp.web.rest;

import br.gov.es.pm.sbp.domain.Diretorio;
import br.gov.es.pm.sbp.security.AuthoritiesConstants;
import br.gov.es.pm.sbp.service.DiretorioService;
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
 * REST controller for managing {@link br.gov.es.pm.sbp.domain.Diretorio}.
 */
@RestController
@RequestMapping("/api")
public class DiretorioResource {

    private final Logger log = LoggerFactory.getLogger(DiretorioResource.class);

    private static final String ENTITY_NAME = "diretorio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiretorioService diretorioService;

    public DiretorioResource(DiretorioService diretorioService) {
        this.diretorioService = diretorioService;
    }

    /**
     * {@code POST  /diretorios} : Create a new diretorio.
     *
     * @param diretorio the diretorio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diretorio, or with status {@code 400 (Bad Request)} if the diretorio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diretorios")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Diretorio> createDiretorio(@Valid @RequestBody Diretorio diretorio) throws URISyntaxException {
        log.debug("REST request to save Diretorio : {}", diretorio);
        if (diretorio.getId() != null) {
            throw new BadRequestAlertException("A new diretorio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diretorio result = diretorioService.save(diretorio);
        return ResponseEntity.created(new URI("/api/diretorios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getNome()))
            .body(result);
    }

    /**
     * {@code PUT  /diretorios} : Updates an existing diretorio.
     *
     * @param diretorio the diretorio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diretorio,
     * or with status {@code 400 (Bad Request)} if the diretorio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diretorio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diretorios")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Diretorio> updateDiretorio(@Valid @RequestBody Diretorio diretorio) throws URISyntaxException {
        log.debug("REST request to update Diretorio : {}", diretorio);
        if (diretorio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Diretorio result = diretorioService.save(diretorio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diretorio.getNome()))
            .body(result);
    }

    /**
     * {@code GET  /diretorios} : get all the diretorios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diretorios in body.
     */
    @GetMapping("/diretorios")
    public ResponseEntity<List<Diretorio>> getAllDiretorios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Diretorios");
        Page<Diretorio> page = diretorioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /diretorios/:id} : get the "id" diretorio.
     *
     * @param id the id of the diretorio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diretorio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diretorios/{id}")
    public ResponseEntity<Diretorio> getDiretorio(@PathVariable Long id) {
        log.debug("REST request to get Diretorio : {}", id);
        Optional<Diretorio> diretorio = diretorioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diretorio);
    }

    /**
     * {@code DELETE  /diretorios/:id} : delete the "id" diretorio.
     *
     * @param id the id of the diretorio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diretorios/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteDiretorio(@PathVariable Long id) {
        log.debug("REST request to delete Diretorio : {}", id);
        diretorioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/diretorios?query=:query} : search for the diretorio corresponding
     * to the query.
     *
     * @param query the query of the diretorio search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/diretorios")
    public ResponseEntity<List<Diretorio>> searchDiretorios(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Diretorios for query {}", query);
        Page<Diretorio> page = diretorioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
