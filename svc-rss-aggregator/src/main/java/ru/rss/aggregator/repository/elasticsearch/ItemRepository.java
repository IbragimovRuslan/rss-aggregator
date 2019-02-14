package ru.rss.aggregator.repository.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.rss.aggregator.model.elastic.Item;

import java.util.List;

@Repository
@Transactional
public interface ItemRepository extends CommonElasticsearchRepository<Item, String> {
    //@Query("{\"bool\" : {\"must\" : {\"field\" : {\"jsonItem\" : {\"query\" : \"?\",\"analyze_wildcard\" : true}}}}}")
    List<Item> findByJsonItemContaining(String jsonItem);
}
