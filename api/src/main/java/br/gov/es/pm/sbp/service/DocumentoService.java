package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.config.ApplicationProperties;
import br.gov.es.pm.sbp.domain.Diretorio;
import br.gov.es.pm.sbp.domain.Documento;
import br.gov.es.pm.sbp.domain.Pagina;
import br.gov.es.pm.sbp.filedatabase.DocumentoFileDatabase;
import br.gov.es.pm.sbp.repository.DiretorioRepository;
import br.gov.es.pm.sbp.repository.DocumentoRepository;
import br.gov.es.pm.sbp.repository.search.DocumentoSearchRepository;
import br.gov.es.pm.sbp.security.SecurityUtils;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Documento}.
 */
@Service
@Transactional
public class DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoService.class);
    private final DocumentoRepository documentoRepository;
    private final DocumentoSearchRepository documentoSearchRepository;
    private final DocumentoFileDatabase documentoFileDatabase;
    private final PDFManipulator pdfManipulator;
    private final PaginaService paginaService;
    private final ApplicationProperties applicationProperties;
    private final DiretorioRepository diretorioRepository;
    private final DiretorioService diretorioService;

    public DocumentoService(
        DocumentoRepository documentoRepository,
        DocumentoSearchRepository documentoSearchRepository,
        DocumentoFileDatabase documentoFileDatabase,
        PDFManipulator pdfManipulator,
        PaginaService paginaService,
        ApplicationProperties applicationProperties,
        DiretorioRepository diretorioRepository,
        DiretorioService diretorioService) {
        this.documentoRepository = documentoRepository;
        this.documentoSearchRepository = documentoSearchRepository;
        this.documentoFileDatabase = documentoFileDatabase;
        this.pdfManipulator = pdfManipulator;
        this.paginaService = paginaService;
        this.applicationProperties = applicationProperties;
        this.diretorioRepository = diretorioRepository;
        this.diretorioService = diretorioService;
    }

    /**
     * Save a documento.
     *
     * @param documento the entity to save.
     * @return the persisted entity.
     */
    public Documento save(Documento documento, MultipartFile arquivo) throws IOException {
        log.debug("Request to save Documento : {}", documento);

        documento = documentoRepository.save(documento);

        Path pdf = documentoFileDatabase.addDocumento(documento.getDiretorio().getNome(),
            documento.getId() + ".pdf", arquivo);

        List<PDDocument> pages = pdfManipulator.split(pdf);
        documento.setQuantidadePaginas(pages.size());

        List<Pagina> paginas = new ArrayList<>(pages.size());
        for (int i = 0; i < pages.size(); i++) {
            paginas.add(Pagina.make(documento, pdfManipulator.getText(pages.get(i)), i + 1));
        }
        paginaService.saveAll(paginas);

        documento.setProcessado(true);
        documento = documentoRepository.save(documento);
        documentoSearchRepository.save(documento);
        return documento;
    }

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Documento> findAll(Pageable pageable) {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll(pageable);
    }


    /**
     * Get one documento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Documento> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id);
    }

    public ResponseEntity<byte[]> findOneWithConteudo(Long id) throws IOException {
        Documento documento = documentoRepository.findById(id).get();
        String nomeArquivo = documento.getNome() + ".pdf";
        String nomeArquivoFisico = documento.getId() + ".pdf";

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/pdf"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
            .body(documentoFileDatabase.getDocumento(documento.getDiretorio().getNome(), nomeArquivoFisico));
    }

    /**
     * Delete the documento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.info("Request to delete Documento : {}", id);
        documentoRepository.findById(id).ifPresent(documento -> {
            log.info("Deletando paginas...");
            paginaService.deleteByDocumento(id);

            log.info("Deletando documento...");
            documentoRepository.deleteById(id);
            documentoSearchRepository.deleteById(id);

            log.info("Deletando arquivo fisico...");
            try {
                documentoFileDatabase.deleteDocumento(
                    documento.getDiretorio().getNome(),
                    documento.getId() + ".pdf");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Documento> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Documentos for query {}", query);
        return documentoSearchRepository.search(queryStringQuery(query), pageable);
    }

    public Optional<Map<String, Diretorio>> findAllDocumentosBatch() throws IOException {
        Map<String, Diretorio> diretorioMap = new HashMap<>();

        Files.walk(Paths.get(applicationProperties.getBatchFilesPath()))
            .filter(path -> path.toString().toLowerCase().endsWith(".pdf"))
            .forEach(path -> {
                String nomeArq = path.getFileName().toString();
                Documento documento = new Documento();
                documento.setNome(nomeArq.substring(0, nomeArq.lastIndexOf('.')));
                documento.setPath(path.getParent().toString().replace(applicationProperties.getBatchFilesPath(), ""));
                String arrayPath[] = documento.getPath().split(Pattern.quote(File.separator));
                documento.setDataPublicacao(LocalDate.ofYearDay(Integer.parseInt(arrayPath[arrayPath.length - 1]), 1));
                String nomeDir = arrayPath[0];
                if(!diretorioMap.containsKey(nomeDir)) {
                    Diretorio diretorio = new Diretorio();
                    diretorio.setNome(nomeDir);
                    diretorioMap.put(nomeDir, diretorio);
                }

                diretorioMap.get(nomeDir).addDocumento(documento);

            });

        return Optional.of(diretorioMap);
    }

    public Diretorio getOrCreateDiretorio(String nome) {
        Optional<Diretorio> diretorioOptional = diretorioRepository.findOneByNomeIgnoreCase(nome);
        if (!diretorioOptional.isPresent()) {
            Diretorio diretorio = new Diretorio();
            diretorio.setNome(nome);
            diretorio.setRestrito(false);
            diretorio.filtroData(true);
            diretorio.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            return diretorioService.save(diretorio);
        }

        return diretorioOptional.get();
    }

    public Documento saveBatch(Documento documento) throws Exception {
        String arrayPath[] = documento.getPath().split(Pattern.quote(File.separator));
        Diretorio diretorio = getOrCreateDiretorio(arrayPath[0]);
        documento.setDiretorio(diretorio);
        documento = documentoRepository.save(documento);

        try {
            String nomeArquivo = applicationProperties.getBatchFilesPath() + documento.getPath()
                + File.separator + documento.getNome() + ".pdf";

            Path pdf = Paths.get(nomeArquivo);
            List<PDDocument> pages = pdfManipulator.split(pdf);
            documento.setQuantidadePaginas(pages.size());

            List<Pagina> paginas = new ArrayList<>(pages.size());
            for (int i = 0; i < pages.size(); i++) {
                paginas.add(Pagina.make(documento, pdfManipulator.getText(pages.get(i)), i + 1));
            }

            paginaService.saveAll(paginas);

            documento.setProcessado(true);
            documento = documentoRepository.save(documento);
            documentoSearchRepository.save(documento);

            documentoFileDatabase.addDocumento(documento.getDiretorio().getNome(),
                    documento.getId() + ".pdf", nomeArquivo);
        } catch (Exception e) {
            log.error("Erro no documento {}", documento.getDiretorio().getNome() + "/" + documento.getNome());
            paginaService.deleteByDocumento(documento.getId());
            documentoRepository.delete(documento);
            documentoSearchRepository.delete(documento);
            throw e;
        }

        return documento;
    }

    public ResponseEntity<byte[]> findRangeWithConteudo(
        Long id, Integer paginaInicial, Integer paginaFinal)throws IOException {
        Documento documento = documentoRepository.findById(id).get();
        String nomeArquivo = documento.getNome() + "-p" + paginaInicial + "-" + paginaFinal + ".pdf";
        String nomeArquivoFisico = documento.getId() + ".pdf";

        PDDocument document = pdfManipulator.extract(
            documentoFileDatabase.getPathDocumento(documento.getDiretorio().getNome(), nomeArquivoFisico),
            paginaInicial, paginaFinal
        );

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/pdf"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
            .body(pdfManipulator.toByteArray(document));
    }

    /**
     * Get all the documentos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Documento> findAllFull() {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAllByOrderByNomeDesc();
    }
}
