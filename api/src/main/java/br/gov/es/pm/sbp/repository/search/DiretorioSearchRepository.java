package br.gov.es.pm.sbp.repository.search;

import br.gov.es.pm.sbp.domain.Diretorio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Diretorio} entity.
 */
public interface DiretorioSearchRepository extends ElasticsearchRepository<Diretorio, Long> {
}
