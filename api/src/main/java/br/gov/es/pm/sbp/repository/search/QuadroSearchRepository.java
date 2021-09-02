package br.gov.es.pm.sbp.repository.search;

import br.gov.es.pm.sbp.domain.Quadro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Quadro} entity.
 */
public interface QuadroSearchRepository extends ElasticsearchRepository<Quadro, Long> {
}
