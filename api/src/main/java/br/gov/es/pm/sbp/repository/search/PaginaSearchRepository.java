package br.gov.es.pm.sbp.repository.search;

import br.gov.es.pm.sbp.domain.Pagina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Pagina} entity.
 */
public interface PaginaSearchRepository extends ElasticsearchRepository<Pagina, Long> {
    void deleteByDocumentoId(Long id);
}
