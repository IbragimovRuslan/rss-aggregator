package ru.rss.aggregator.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CommonElasticsearchRepository<T, ID extends Serializable> extends ElasticsearchCrudRepository<T, ID> {
}
