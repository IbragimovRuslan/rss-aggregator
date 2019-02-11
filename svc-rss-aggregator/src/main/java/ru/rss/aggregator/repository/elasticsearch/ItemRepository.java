package ru.rss.aggregator.repository.elasticsearch;

import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.elastic.Item;

@Repository
public interface ItemRepository extends CommonElasticsearchRepository<Item, String> {
}
