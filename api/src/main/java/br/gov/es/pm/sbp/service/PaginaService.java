package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.domain.LogBusca;
import br.gov.es.pm.sbp.domain.Pagina;
import br.gov.es.pm.sbp.filedatabase.DocumentoFileDatabase;
import br.gov.es.pm.sbp.repository.PaginaRepository;
import br.gov.es.pm.sbp.repository.search.PaginaSearchRepository;
import br.gov.es.pm.sbp.util.PDFManipulator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pagina}.
 */
@Service
@Transactional
public class PaginaService {

    private final Logger log = LoggerFactory.getLogger(PaginaService.class);
    private final PaginaRepository paginaRepository;
    private final PaginaSearchRepository paginaSearchRepository;
    private final DocumentoFileDatabase documentoFileDatabase;
    private final PDFManipulator pdfManipulator;
    private final LogBuscaService logBuscaService;

    public PaginaService(
        PaginaRepository paginaRepository,
        PaginaSearchRepository paginaSearchRepository,
        DocumentoFileDatabase documentoFileDatabase,
        PDFManipulator pdfManipulator,
        LogBuscaService logBuscaService) {
        this.paginaRepository = paginaRepository;
        this.paginaSearchRepository = paginaSearchRepository;
        this.documentoFileDatabase = documentoFileDatabase;
        this.pdfManipulator = pdfManipulator;
        this.logBuscaService = logBuscaService;
    }

    /**
     * Save a pagina.
     *
     * @param pagina the entity to save.
     * @return the persisted entity.
     */
    public Pagina save(Pagina pagina) {
        log.debug("Request to save Pagina : {}", pagina);
        String conteudo = pagina.getConteudo();
        pagina.setConteudo(null);
        pagina = paginaRepository.save(pagina);
        pagina.setConteudo(conteudo);
        paginaSearchRepository.save(pagina);
        pagina.setConteudo(null);
        return pagina;
    }

    public List<Pagina> saveAll(List<Pagina> paginas) {
        log.debug("Request to save Pagina : {}", paginas);
        paginas = paginaRepository.saveAll(paginas);
        paginaSearchRepository.saveAll(paginas);

        return paginas;
    }

    /**
     * Get all the paginas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Pagina> findAll(Pageable pageable) {
        log.debug("Request to get all Paginas");
        return paginaRepository.findAll(pageable);
    }


    /**
     * Get one pagina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pagina> findOne(Long id) {
        log.debug("Request to get Pagina : {}", id);
        return paginaRepository.findById(id);
    }

    /**
     * Delete the pagina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pagina : {}", id);
        paginaRepository.deleteById(id);
        paginaSearchRepository.deleteById(id);
    }

    /**
     * Search for the pagina corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Pagina> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Paginas for query {}", query);
        Page<Pagina> page;

        LogBusca logBusca = new LogBusca();
        logBusca.setConteudoBuscado(query);
        long inicio = System.currentTimeMillis();

        try {
            page = paginaSearchRepository.search(queryStringQuery(query), pageable);
        } catch (Exception e) {
            // Gravar Log
            logBusca.setQuantidadeEncontrada(0);
            logBusca.setTempoMs(System.currentTimeMillis() - inicio);
            logBusca.setErro(true);
            logBusca.setErroMsg(e.getMessage());
            logBuscaService.save(logBusca);
            throw e;
        }

        // Gravar Log
        if (pageable.getPageNumber() == 0) {
            logBusca.setQuantidadeEncontrada((int) page.getTotalElements());
            logBusca.setTempoMs(System.currentTimeMillis() - inicio);
            logBuscaService.save(logBusca);
        }

        return page;
    }

    public void deleteByDocumento(Long id) {
        paginaRepository.deleteByDocumentoId(id);
        paginaSearchRepository.deleteByDocumentoId(id);
    }
}
