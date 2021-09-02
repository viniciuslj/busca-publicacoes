package br.gov.es.pm.sbp.repository.search;

import br.gov.es.pm.sbp.domain.Cargo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cargo} entity.
 */
public interface CargoSearchRepository extends ElasticsearchRepository<Cargo, Long> {
}
