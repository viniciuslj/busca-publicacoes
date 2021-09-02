package br.gov.es.pm.sbp.repository.search;

import br.gov.es.pm.sbp.domain.LogBusca;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LogBusca} entity.
 */
public interface LogBuscaSearchRepository extends ElasticsearchRepository<LogBusca, Long> {
}
