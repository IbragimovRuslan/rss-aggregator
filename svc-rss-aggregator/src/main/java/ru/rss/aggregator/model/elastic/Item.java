package ru.rss.aggregator.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import ru.rss.aggregator.model.common.ElasticEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "rss_index", type = "item")
public class Item extends ElasticEntity {
    private String jsonItem;
}
