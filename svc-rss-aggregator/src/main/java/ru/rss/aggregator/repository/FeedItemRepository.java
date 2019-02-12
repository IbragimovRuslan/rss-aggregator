package ru.rss.aggregator.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rss.aggregator.model.FeedItem;

import java.util.List;

@Repository
public interface FeedItemRepository extends CommonRepository<FeedItem, Long> {
    List<FeedItem> findBySubscriptionId(Long subscriptionId);

    @Query(value="select * from feed_item where item->>?1 like ?2", nativeQuery=true)
    List<FeedItem> getByFieldContainingString(String field, String substring);
}
