package br.gov.es.pm.sbp.web.rest;

import br.gov.es.pm.sbp.domain.Diretorio;
import br.gov.es.pm.sbp.domain.Documento;
import br.gov.es.pm.sbp.security.AuthoritiesConstants;
import br.gov.es.pm.sbp.service.DocumentoService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link br.gov.es.pm.sbp.domain.Documento}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);

    private static final String ENTITY_NAME = "documento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoService documentoService;

    public DocumentoResource(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    /**
     * {@code POST  /documentos} : Create a new documento.
     *
     * @param documento the documento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documento, or with status {@code 400 (Bad Request)} if the documento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Documento> createDocumento (
        @Valid @RequestPart Documento documento,
        @RequestPart MultipartFile arquivo) throws URISyntaxException, IOException {
        log.debug("REST request to save Documento : {}", documento);
        log.debug("Tamanho Arquivo: {} bytes", arquivo.getSize());

        if (documento.getId() != null) {
            throw new BadRequestAlertException("A new documento cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Documento result = documentoService.save(documento, arquivo);

        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentos} : Updates an existing documento.
     *
     * @param documento the documento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documento,
     * or with status {@code 400 (Bad Request)} if the documento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
//    @PutMapping("/documentos")
//    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
//    public ResponseEntity<Documento> updateDocumento(
//        @Valid @RequestBody Documento documento) throws URISyntaxException, IOException {
//        log.debug("REST request to update Documento : {}", documento);
//        if (documento.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        Documento result = documentoService.save(documento, null);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documento.getId().toString()))
//            .body(result);
//    }

    /**
     * {@code GET  /documentos} : get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentos in body.
     */
    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> getAllDocumentos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Documentos");
        Page<Documento> page = documentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/documentos/all")
    public ResponseEntity<List<Documento>> getAllDocumentosFull() {
        log.debug("REST request to get all Documentos");
        List<Documento> documentos = documentoService.findAllFull();
        return ResponseEntity.ok().body(documentos);
    }

    /**
     * {@code GET  /documentos/:id} : get the "id" documento.
     *
     * @param id the id of the documento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos/{id}")
    public ResponseEntity<Documento> getDocumento(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        Optional<Documento> documento = documentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documento);
    }

    @GetMapping("/documentos/{id}/$conteudo")
    public ResponseEntity<byte[]> getDocumentoConteudo(@PathVariable Long id) throws IOException {
        log.debug("REST request to get Conteudo Documento : {}", id);
        return documentoService.findOneWithConteudo(id);
    }

    /**
     * {@code DELETE  /documentos/:id} : delete the "id" documento.
     *
     * @param id the id of the documento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        log.debug("REST request to delete Documento : {}", id);
        documentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/documentos?query=:query} : search for the documento corresponding
     * to the query.
     *
     * @param query the query of the documento search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/documentos")
    public ResponseEntity<List<Documento>> searchDocumentos(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Documentos for query {}", query);
        Page<Documento> page = documentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/documentos/batch")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Map<String, Diretorio>> getAllDocumentosBatch()throws IOException {
        log.debug("REST request getAllDocumentosBatch");
        return ResponseUtil.wrapOrNotFound(documentoService.findAllDocumentosBatch());
    }

    @PostMapping("/documentos/batch")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Documento> createDocumentoBatch (@RequestBody Documento documento)
        throws Exception {
        log.debug("REST request createDocumentoBatch : {}", documento);

        Documento result = documentoService.saveBatch(documento);

        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/documentos/{id}/{paginaInicial}/{paginaFinal}/$conteudo")
    public ResponseEntity<byte[]> getPaginasConteudo(
        @PathVariable Long id, @PathVariable Integer paginaInicial, @PathVariable Integer paginaFinal)
        throws Exception {
        log.debug("REST request to getPaginasConteudo : {}-{}-{}", id, paginaInicial, paginaFinal);
        return documentoService.findRangeWithConteudo(id, paginaInicial, paginaFinal);
    }
}
